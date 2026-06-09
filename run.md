# Running the Project

This guide explains how to deploy and access the complete microservices application locally using Kind, Helm, and ArgoCD.

## Prerequisites

Ensure the following tools are installed:

* Docker
* Java 21+
* Maven
* Git
* kubectl
* Kind
* Helm

Verify installation:

```bash
docker --version
java --version
mvn --version
kubectl version --client
kind version
helm version
```

---

## Clone the Repository

```bash
git clone <repository-url>
cd end-to-end
```

---

## Create the Kind Cluster

This project uses a custom Kind configuration that maps Kubernetes NodePort `30080` directly to the host machine.

```yaml
kind: Cluster
apiVersion: kind.x-k8s.io/v1alpha4

nodes:
  - role: control-plane
    extraPortMappings:
      - containerPort: 30080
        hostPort: 30080
        protocol: TCP
```

Create the cluster:

```bash
kind create cluster --name gitops-cluster --config kind-config.yaml
```

Verify:

```bash
kubectl get nodes
```

Expected output:

```text
NAME                           STATUS   ROLES           AGE
gitops-cluster-control-plane   Ready    control-plane   XXs
```

---

## Install ArgoCD

Create the ArgoCD namespace:

```bash
kubectl create namespace argocd
```

Install ArgoCD:

```bash
kubectl apply -n argocd \
-f https://raw.githubusercontent.com/argoproj/argo-cd/stable/manifests/install.yaml
```

Verify installation:

```bash
kubectl get pods -n argocd
```

Wait until all pods are in the `Running` state.

---

## Access ArgoCD UI

Port-forward the ArgoCD server:

```bash
kubectl port-forward svc/argocd-server -n argocd 8080:443
```

Open:

```text
https://localhost:8080
```

Retrieve the initial admin password:

```bash
kubectl -n argocd get secret argocd-initial-admin-secret \
-o jsonpath="{.data.password}" | base64 -d
```

Login credentials:

```text
Username: admin
Password: <output-from-command>
```

---

## Deploy Applications

Apply the ArgoCD application manifests:

```bash
kubectl apply -f argocd/mysql.yaml
kubectl apply -f argocd/user-service.yaml
kubectl apply -f argocd/order-service.yaml
kubectl apply -f argocd/api-gateway.yaml
```

Verify applications:

```bash
kubectl get applications -n argocd
```

---

## Verify Deployment

Check running pods:

```bash
kubectl get pods
```

Check services:

```bash
kubectl get svc
```

Expected services:

* mysql
* user-service
* order-service
* api-gateway

---

## Access the Application

The API Gateway is exposed through a Kubernetes NodePort.

Because the Kind cluster maps NodePort `30080` directly to the host machine, no additional port-forwarding is required.

Open:

```text
http://localhost:30080
```

---

## Test APIs

### User Service

```bash
curl http://localhost:30080/users
```

### Order Service

```bash
curl http://localhost:30080/orders
```

All requests are routed through the API Gateway and forwarded to the appropriate backend microservice.

---

## CI/CD and GitOps Workflow

The project follows a GitOps deployment model.

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

Whenever changes are merged into the main branch:

1. GitHub Actions builds all services.
2. Docker images are created and tagged.
3. Images are pushed to Docker Hub.
4. Helm chart image tags are updated.
5. GitOps configuration is committed.
6. ArgoCD detects the changes automatically.
7. Kubernetes resources are synchronized.
8. The latest application version is deployed to the Kind cluster.

---

## Cleanup

Delete the cluster:

```bash
kind delete cluster --name gitops-cluster
```

Verify removal:

```bash
kind get clusters
```

The cluster should no longer appear in the list.
