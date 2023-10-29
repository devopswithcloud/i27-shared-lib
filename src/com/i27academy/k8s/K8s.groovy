package com.i27academy.k8s

class K8s {
    def jenkins 
    K8s(jenkins) {
        this.jenkins = jenkins
    }

    def auth_login(gke_cluster_name, gke_zone, gke_project){
        jenkins.sh"""#!/bin/bash
        echo "Entering Auth Method for k8s Login"
        gcloud config set account jenkins-gke-svc-account@practical-brace-402514.iam.gserviceaccount.com
        #gcloud auth activate-service-account jenkins-gke-svc-account@practical-brace-402514.iam.gserviceaccount.com --key-file=key.json
        gcloud compute instances list
        echo "***************** Showing Number of Worker node in cluster *****************"
        gcloud container clusters get-credentials $gke_cluster_name --zone $gke_zone --project $gke_project
        kubectl get nodes 
        """
    }
    def k8sdeploy(fileName, docker_image) {
        jenkins.sh """#!/bin/bash
        echo "Executing K8S Deploy Method"
        echo "Final image tag is $docker_image"
        sed -i "s|DIT|$docker_image|g" ./.cicd/$fileName
        kubectl apply -f ./.cicd/$fileName
        """
    }
    def k8sHelmChartDeploy() {
        jenkins.sh """#!/bin/bash
        echo "********************* Helm Groovy Method from Groovy *********************"
        """
    }
    def gitClone(creds) {
        jenkins.sh """#!/bin/bash
        echo "********************* Entering Git Clone Method from Groovy *********************"
        git clone -b master https://${creds}@github.com/devopswithcloud/i27-shared-lib.git
        ls -la
        """
    }
}