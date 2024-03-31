### install dokcer desktop
### install kubectl
### install minikube

# Create the docker and push it to repository on dockerhub

mvn package

docker build -t jjbasile/team8:latest .

docker push jjbasile/team8

# Use the docker image in kubernetes

minikube start

kubectl create -f pod-definition.yml

# Validate the execution and output

kubectl get deployments

kubectl get pods

kubectl get logs <pod-name>

kubectl exec -it <pod-name> -- sh

    ls

    ## Wait for the expected time of execution before opening the output file

    vi thread-1-output.txt

        ## quit the file without modifying

        `esc` :q! `enter`

kubectl delete deployment team8-deployment

minikube stop

docker system prune

docker rmi <image-name:image-tag>

docker images
