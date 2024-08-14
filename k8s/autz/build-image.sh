# Запускать из корневой директории

# Построение образа autz-app
minikube image build -f docker/ServiceApp.dockerfile -t autz-app --build-opt=build-arg=APP_NAME=autz --build-opt=build-arg=SERVICE_PATH=/services .
