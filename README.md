


# My Boutique kubernetes

My-boutique is an application developped following the book "Playing with Java Microservices on Kubernetes and OpenShift" writen by Nebrass Lamouchi. <br>

In addition to the app from the book I added authorization in each microservice and created a microservice called jwt-service for authentication.
<br>
There is also an open-source front-end angular application that I developped and that you can have a look at this [URL](https://github.com/MootezElj/my-boutique-angular). 
This guide will show you how to deploy My-boutique app with an authentication service to kubernete manually (without fabric8). By the end of this guide, you will be able to deploy **any** java application to k8s.

The application you get represents a secured rest APIs of 3 major microservices (product-service, order-service and customer-service) each of this services will be deployed to k8s (kubernetes) using externalized configuration aka 'config-map' ( and of course with the jwt-service wich will be deployed to k9s as well) .

Notes:
- For the tests, I'm working on a front-end app but I will explain in more details how things should be done to use the app.
- ##### I'm using elementary os (Ubuntu 16.04)

# Contents:
1. **Requirements**
2. **Get the app**
3.  **Run the app**
4.  **Make docker image for each ms**
5.  **Deploy to kubernetes**
6.  **Test**
7.  **How the app works**

# 1.Requirements!
In order to run the app you need:
  - an IDE ( I use STS 4.3.0).
  - JAVA 8
  - Docker  (v 19.03.1 used)
  - k8s and minikube (for creating the cluster)

# 2. get the app:
 ####  &nbsp;&nbsp;&nbsp;&nbsp;  1. clone the repository
 the first step is to clone the project so open your terminal and type:
 ```sh
$ git clone https://github.com/MootezElj/my-boutique-kubernetes.git
$ cd my-boutique-kubernetes/
```
 ####  &nbsp;&nbsp;&nbsp;&nbsp;  2. Compile the apps
the first step is to compile my-boutique-commons because it's a dependency used by all of the ms.
 ```sh
$ cd myboutique-commons/
$ mvn clean install
$ cd ../
```

now you need to do the same for each microservice (**jwt**, order, product and customer) 
(expl: product-service):
 ```sh
$ cd product-service-kubernetes/
$ mvn clean install
$ cd ../
```

&nbsp;
&nbsp;
>To make things clear, when I say microservices I mean order-service, product-service, customer-service and jwt-service. 
>my-boutique-commons is not a microservice it's a depency used by all the ms except jwt-service. I just wanted to be clear because I don't want to be listing all the micrservices each time.

# 3. run the app:
##### after compiling the 5 apps we should run our microservices to test them:
to do this, open 4 terminal instances in every service directory and run the app:
(expl product-service, you should do this for the 4 services)
 
 ```sh
$ cd product-service-kubernetes/
$ mvn spring-boot:run
```

once every server is running, we can test them through these steps:
go to your web browser and open these URLs: 
- https://localhost:9991/swagger-ui.html#/
    this is a [Swagger] ui for the product service. (Swagger is a tool that helps us document and test our resources) .
- https://localhost:9992/swagger-ui.html#/
    this is a swagger ui for the order service
- https://localhost:9993/swagger-ui.html#/
    this is a swagger ui for the customer service.
- http://localhost:8083/jwt/admin/
    this is an endpoint exposed by the jwt-service.

By now we have a complete secured application that is ready to be used.
now let's deploy it  to kubernetes.

Note: 
>If one of these URLs don't work, please contact me at eljmootez@gmail.com to try to resolve the issue and to see how we can improve the app.

# 4.  make docker image for each ms
 ####  &nbsp;&nbsp;&nbsp;&nbsp;  1. Prepare each ms
you shoud stop all the services ( we were running them just for test purpose).

now, in kubernetes we will be using configmaps for ech microservice's server port. So we need to remove the "application.properties" file of our microservices. To do that, go to "src/main/resources/" of each ms and delete "application.properties"



 ####  &nbsp;&nbsp;&nbsp;&nbsp;  2. build the image
Before building the images you need to go to [Docker Hub] and create an account. Docker Hub is like github but it contains docker images.

after creating the account open a terminal instance and type:
```sh
$ docker login
```
provide your credantials, after loging in follow these steps in every ms in order to build the images in your docker hub account.

**Follow these steps for each ms:**

1. Open the ms in your IDE
2. we will be using [jib] wich is "an open-source Java containerizer from Google" to make an image of our ms.
if you go to the pom.xml file in each ms you will find this portion of code:
```sh
		<plugin>
				<groupId>com.google.cloud.tools</groupId>
				<artifactId>jib-maven-plugin</artifactId>
				<version>0.9.10</version>
				<configuration>
					<to>
						<image>mootezelj/${project.artifactId}:${project.version}</image>
					</to>
				</configuration>
			</plugin>
```

this is how we use jib depency. "mootezelj" is the username that you want to use in order to deploy the image ( this should be your docker hub *username*). So you replace that "mootezelj" with yours.

3.  make an ``$ mvn clean install `` for the ms.

4. Go to the ms folder open a terminal instance and type the following:
```sh
$ mvn compile jib:dockerBuild
```

### Testing:
- After successfully building every image, type:

```sh
$ docker images
```
You should get these images (with "mootezelj" replaced with your username):
```sh
mootezelj/customer-service           0.0.1-SNAPSHOT      045c15ff4b98        49 years ago        185MB
mootezelj/order-service              0.0.1-SNAPSHOT      3db049743d0b        49 years ago        185MB
mootezelj/product-service            0.0.1-SNAPSHOT      94d6c88d27e7        49 years ago        189MB
mootezelj/jwt-service                0.0.1-SNAPSHOT      b9d023f09ce0        49 years ago        167MB
```
# 5. deploy to kubernetes:
After building our micrservices images to the docker, we are ready to deploy those images to k8s.
to do so, we will be using ms-k8s folders. Each folder has the basic configuration needed for each ms.

1. Before we do anything you need to open these folders and go to the deployment.yml files and replace  the "mootezelj" 
in:

``image: mootezelj/jwt-service:0.0.1-SNAPSHOT``
with your username to have something like this (let's say your username is johnpery)
you will be having:
``image: johnpery/jwt-service:0.0.1-SNAPSHOT``

> you need to do this in the following files:
> jwtDeployment.yml, orderDeployment.yml, product-deployment.yml and customer-deployment.yml


 2. go to the repository folder (/my-boutique-kubernetes) and open a terminal instance inside that folder and type:
```sh
$ minikube start
```
this will run our k8s cluster. 

 3. type this in the terminal to deploy ms:
- Product service
```sh
$ kubectl create -f product-k8s/product-configMap.yml
$ kubectl create -f product-k8s/product-service.yml
$ kubectl create -f product-k8s/product-deployment.yml
```
this will deploy product-service to k8s we should the same for the 3 others:
- Order Service

```sh
$ kubectl create -f order-k8s/orderConfigMap.yml
$ kubectl create -f order-k8s/orderService.yml
$ kubectl create -f order-k8s/orderDeployment.yml
```

- Customer Service:
```sh
$ kubectl create -f customer-k8s/customer-configMap.yml
$ kubectl create -f customer-k8s/customer-service.yml
$ kubectl create -f customer-k8s/customer-deployment.yml 
```
- Jwt service:
```sh
$ kubectl create -f jwt-k8s/jwtConfigMap.yml
$ kubectl create -f jwt-k8s/jwtService.yml
$ kubectl create -f jwt-k8s/jwtDeployment.yml
```

# 6. Test
now let us test if everything went as desired.
typing:
```sh
$ kubectl get configmaps
```
will give you this:
```sh
NAME               DATA   AGE
customer-service   1      5m50s
jwt-service        1      3m41s
order-service      1      7m12s
product-service    1      10m
```
-------
```sh
$ kubectl get services
```
will give you this:
```sh
NAME               TYPE           CLUSTER-IP      EXTERNAL-IP   PORT(S)          AGE
customer-service   LoadBalancer   10.96.8.72      <pending>     9992:30005/TCP   6m44s
jwt-service        LoadBalancer   10.96.205.121   <pending>     9995:30009/TCP   4m9s
kubernetes         ClusterIP      10.96.0.1       <none>        443/TCP          22d
order-service      LoadBalancer   10.97.187.37    <pending>     9993:30006/TCP   8m11s
product-service    LoadBalancer   10.105.223.96   <pending>     9991:30004/TCP   11m
```
----
```sh
$ kubectl get deployments
```
will give you this:
```sh
NAME               READY   UP-TO-DATE   AVAILABLE   AGE
customer-service   3/3     3            3           7m26s
jwt-service        0/3     3            0           4m39s
order-service      3/3     3            3           8m53s
product-service    0/1     1            0           11m
```

it takes some time to complete the deployment and run the apps ( about 10 minutes for me) .

To complete the test open a terminal and type:
```sh
$ minikupe ip
```
you will get an ip (192.168.99.100). Copy the ip, open a web browser and go to:


- http://192.168.99.100:30006/swagger-ui.html
    swagger ui for the order service
- http://192.168.99.100:30005/swagger-ui.html
    swagger ui for the customer service.
 - http://192.168.99.100:30004/swagger-ui.html
    swagger ui for the product service.
- http://192.168.99.100:30009/jwt/admin/
    endpoint exposed by the jwt-service.
    
    by going, to our previous tests you can see the difference:
    We are replacing the localhost with the minikube host and the port with our the service's port that is accessible outside the cluster.

you can run this command to get the kubernetes dashboard:
```sh
$ minikube dashboard
```

# 7.How the app works: 
In this section I will explain how the app works.
1. Some endpoints require jwt tokens (you can see the endpoints by opening the projects and taking a look at the security config).
2. The token can be accessible via a Post request to:
http://192.168.99.100:30009/login/ (jwt-service)
you should send a json object containing a username and a password:
``
{"username":"admin","password":"admin123"}
``
In the response you will have an authorization header the value will be like so: "Bearer yourToken"
you can access users and roles in the config of jwt-service project.
3. When a request of a ms requires a token. The token accessed through jwt-service should be sent in the request header as an "Authorization" with the bearer prefix. In other words you should send the value recieved when you requested your token.

>If you will be using the app you should secure the swagger ui, since I exposed to everyone to test the apps.

Thank you, Enjoy !!
	

   [Swagger]: <https://swagger.io/>
   [jib]: <https://cloud.google.com/blog/products/gcp/introducing-jib-build-java-docker-images-better>
[Docker Hub]:<https://hub.docker.com/>
   [ Understanding How the Docker Daemon and Docker CLI Work Together]:<https://nickjanetakis.com/blog/understanding-how-the-docker-daemon-and-docker-cli-work-together>
********

