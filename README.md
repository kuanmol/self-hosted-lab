# End-to-End GitOps CI/CD Pipeline for Spring Boot Microservices on Kubernetes

## Overview

This project demonstrates a complete end-to-end DevOps and GitOps workflow for deploying a Spring Boot microservices application on Kubernetes using GitHub Actions, Docker Hub, Helm, ArgoCD, and Kind.

The application follows a microservices architecture and consists of:

* API Gateway (Spring Cloud Gateway)
* User Service
* Order Service
* MySQL Database

All client requests are routed through the API Gateway, which acts as the single entry point to the system and forwards requests to the appropriate backend services.

The project implements a complete CI/CD pipeline where code changes automatically trigger Docker image builds, image publishing to Docker Hub, GitOps updates, and Kubernetes deployments through ArgoCD.

---

## Architecture

```text
                    Client
                       |
                       ▼
              +----------------+
              |  API Gateway   |
              +--------+-------+
                       |
          +------------+------------+
          |                         |
          ▼                         ▼
 +----------------+      +----------------+
 |  User Service  |      | Order Service  |
 +--------+-------+      +--------+-------+
          |                       |
          +-----------+-----------+
                      |
                      ▼
               +-------------+
               |    MySQL    |
               +-------------+
```

---

## CI/CD & GitOps Workflow

```text
Developer Push
       │
       ▼
GitHub Repository
       │
       ▼
GitHub Actions
       │
       ├── Build Spring Boot Applications
       ├── Build Docker Images
       └── Push Images to Docker Hub
       │
       ▼
Update Helm Chart Image Tags
       │
       ▼
GitOps Repository
       │
       ▼
ArgoCD Watches Repository
       │
       ▼
Automatic Synchronization
       │
       ▼
Kind Kubernetes Cluster
```

---

## Technologies Used

### Application Layer

* Java 21
* Spring Boot
* Spring Cloud Gateway
* Spring Data JPA
* Maven

### Database

* MySQL

### Containerization

* Docker
* Docker Hub
* Docker Compose

### CI/CD & GitOps

* GitHub Actions
* Helm
* ArgoCD

### Kubernetes

* Kubernetes
* Kind
* kubectl

### Version Control

* Git
* GitHub

---

## Repository Structure

```text
.
├── api-gateway/
├── user-service/
├── order-service/
├── helm/
│   ├── api-gateway/
│   ├── user-service/
│   ├── order-service/
│   └── mysql/
├── argocd/
├── kind-config.yaml
├── docker-compose.yml
├── run.md
└── README.md
```

---

## Key Features

* Spring Boot microservices architecture
* API Gateway pattern implementation
* User and Order management services
* MySQL database integration
* Dockerized applications
* Automated Docker image publishing to Docker Hub
* GitHub Actions CI pipeline
* Helm-based Kubernetes deployments
* GitOps deployment strategy
* ArgoCD continuous synchronization
* Local Kubernetes environment using Kind
* Infrastructure as Code using Kubernetes YAML manifests
* Automated application rollout through GitOps workflows

---

## Deployment Strategy

1. Developer pushes code changes to GitHub.
2. GitHub Actions pipeline is triggered automatically.
3. Docker images are built for all services.
4. Images are tagged and pushed to Docker Hub.
5. Helm chart image tags are updated.
6. GitOps configuration is committed to the repository.
7. ArgoCD detects repository changes.
8. ArgoCD synchronizes the Kubernetes cluster.
9. Updated services are deployed automatically on the Kind cluster.

---

## Learning Outcomes

This project provided hands-on experience with:

* Microservices architecture design
* API Gateway implementation
* Docker image creation and registry management
* CI/CD automation with GitHub Actions
* Kubernetes deployments and service management
* Helm chart development and templating
* GitOps deployment workflows
* ArgoCD application lifecycle management
* Kubernetes networking and service discovery
* Infrastructure as Code (IaC)
* End-to-end DevOps practices and deployment automation

```
```
