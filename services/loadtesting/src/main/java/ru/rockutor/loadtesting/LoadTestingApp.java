package ru.rockutor.loadtesting;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Stream;

@Slf4j
@SpringBootApplication
public class LoadTestingApp implements CommandLineRunner {
    private static final User EDITOR = new User("employee", "q1w2e3r4t5");
    private static final User SIGNER = new User("inspector", "q1w2e3r4t5");

    public static void main(String[] args) {
        SpringApplication.run(LoadTestingApp.class, args);
    }

    private static final List<String> SECTION_NAMES = List.of(
            "PREAMBLE",
            "SUBJECT_CONTRACT",
            "SURETY_OBLIGATIONS",
            "PARTIES_REQUISITES",
            "PENALTIES"
    );

    private static final List<String> ATTRIBUTE_NAMES = List.of(
            "HEADER",
            "BODY",
            "PARAGRAPH_1",
            "PARAGRAPH_2",
            "CONCLUSION"
    );

    private static final String GATEWAY = "http://localhost:7777";

    @Override
    public void run(String... args) {
        // Перед запуском тестов, убедись, что GATEWAY доступен!
        log.info("Начало сценариев");
        repeatScenario(10, this::createUpdateDeleteScenario);
        log.info("Сценарии завершены");
    }

    private void repeatScenario(long times, Runnable scenario) {
        AtomicLong counter = new AtomicLong(times);
        Runnable counterScenario = () -> {
            scenario.run();
            long remaining = counter.decrementAndGet();
            log.info("Осталось - {}", remaining);
        };
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        List<Runnable> tasks = Stream.generate(() -> counterScenario).limit(times).toList();
        tasks.forEach(Runnable::run);
    }

    private void createUpdateScenario() {
        // 1. Авторизоваться, получить токен
        EditorClient client = new EditorClient(EDITOR, GATEWAY);

        // 2. Создать документ
        UUID documentId = client.createDocument();

        // 3. Создать секции
        SECTION_NAMES.forEach(sectionName -> client.createSection(documentId, sectionName));

        // 4. Добавить атрибуты
        SECTION_NAMES.forEach(sectionName ->
                ATTRIBUTE_NAMES.forEach(attributeName ->
                        client.addAttribute(documentId, sectionName, createAttribute(attributeName, "Текст")
                        )
                )
        );
    }

    private void signDocuments() {
        // 1. Авторизоваться,
        SignerClient signerClient = new SignerClient(SIGNER, GATEWAY);

        // 2. Получить запросы
        List<UUID> requests = signerClient.getSigningRequests();

        // 3. Подписать
        requests.forEach(signerClient::signDocumentByRqId);
    }

    private void createAndSendForSigning() {
        // 1. Авторизоваться
        EditorClient editorClient = new EditorClient(EDITOR, GATEWAY);

        // 2. Создать документ
        UUID documentId = editorClient.createDocument();

        // 3. Отправить документ на подпись
        editorClient.sendForSigning(documentId);
    }

    private void createSignScenario() {
        // 1. Авторизоваться
        EditorClient editorClient = new EditorClient(EDITOR, GATEWAY);

        // 2. Создать документ
        UUID documentId = editorClient.createDocument();

        // 3. Отправить документ на подпись
        editorClient.sendForSigning(documentId);

        // 4. Ждем Кафку
        sleep(2000);

        // 5. Подписать
        SignerClient signerClient = new SignerClient(SIGNER, GATEWAY);

        signerClient.signDocumentByDocumentId(documentId);
    }


    private void createUpdateDeleteScenario() {
        // 1. Авторизоваться, получить токен
        EditorClient client = new EditorClient(EDITOR, GATEWAY);

        // 2. Создать документ
        UUID documentId = client.createDocument();

        // 3. Создать секции
        SECTION_NAMES.forEach(sectionName -> client.createSection(documentId, sectionName));

        // 4. Добавить атрибуты
        SECTION_NAMES.forEach(sectionName ->
                ATTRIBUTE_NAMES.forEach(attributeName ->
                        client.addAttribute(documentId, sectionName, createAttribute(attributeName, "Текст")
                        )
                )
        );

        // 5. Удалить секции
        SECTION_NAMES.forEach(sectionName -> client.deleteSection(documentId, sectionName));

        // 6. Удалить документ
        client.deleteDocument(documentId);
    }

    private void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private Attribute createAttribute(String name, String value) {
        return Attribute.builder()
                .name(name)
                .type(AttributeType.TABLE.name())
                .value(value)
                .build();
    }
}
