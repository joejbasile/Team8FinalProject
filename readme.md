# Prequesites 

### install docker desktop
### install kubectl
### install minikube

# Create the docker and push it to repository on dockerhub

mvn package

docker build -t jjbasile/team8:latest .

docker push jjbasile/team8

# Use the docker image in kubernetes

kubectl get node minikube -o jsonpath='{.status.capacity}'

minikube start --memory 8192 --cpus 6

OR

    minikube config set memory 8192
    minikube config set cpus 4
    minikube start

kubectl create -f pod-definition.yml

# Validate the execution and output

kubectl get deployments

kubectl get pods

kubectl logs `pod-name`

kubectl exec -it `pod-name` -- sh

    ls

    ## Wait for the expected time of execution before opening the output file

    vi thread-1-output.txt

        ## quit the file without modifying

        `esc` :q! `enter`

kubectl delete deployment team8-deployment

minikube stop

minikube profile list

minikube delete --profile minikube

docker system prune

docker rmi `image-name:image-tag`

docker images
