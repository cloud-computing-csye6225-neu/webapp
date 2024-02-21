packer {
  required_plugins {
    googlecompute = {
      source  = "github.com/hashicorp/googlecompute"
      version = ">= 1.1"
    }
  }
}

variable "zone" {
  type    = string
  default = "us-east1-b"
}
variable "project_id" {
  type    = string
  default = "csye6225-assignemnt-3"
}
variable "source_image_family" {
  type    = string
  default = "centos-stream-8"
}
variable "network" {
  type    = string
  default = "default"
}
variable "source_ssh_username" {
  type    = string
  default = "packer"
}

variable "DB_URL" {
  type = string
}

variable "DB_USERNAME" {
  type = string
}

variable "DB_PASSWORD" {
  type = string
}

source "googlecompute" "custom-mi" {
  project_id          = var.project_id
  source_image_family = var.source_image_family
  zone                = var.zone
  network             = var.network
  ssh_username        = var.source_ssh_username
  image_name          = "csye6225-{{timestamp}}"
  # disk_size              = "20"
  # disk_type              = "pd-standard"
  # image_description      = "A custom image with webapp pre-installed"
  # image_family           = "csye6255-app-image"
  # image_project_id       = "csye6255-assignemnt-3"
  # image_storage_locations = ["us"]
}

build {
  name = "csye6255-assignemnt-4-centos"
  sources = [
    "source.googlecompute.custom-mi"
  ]

  provisioner "shell" {
    environment_vars = [
      DB_URL="${var.DB_URL}",
      DB_USERNAME="${var.DB_USERNAME}",
      DB_PASSWORD="${var.DB_PASSWORD}"
    ]
    script = "pre-req.sh"
  }


  provisioner "file" {
    source      = "target/healthCheckAPI-0.0.1-SNAPSHOT.jar"
    destination = "/opt/"
  }

  provisioner "file" {
    source      = "csye6225.service"
    destination = "/opt/"
  }

  provisioner "shell"{
    environment_vars = [
      DB_URL="${var.DB_URL}",
      DB_USERNAME="${var.DB_USERNAME}",
      DB_PASSWORD="${var.DB_PASSWORD}"
    ]
    script = "env-setup.sh"
  }
}
