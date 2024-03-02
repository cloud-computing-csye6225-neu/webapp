packer {
  required_plugins {
    googlecompute = {
      source  = "github.com/hashicorp/googlecompute"
      version = ">= 1.1"
    }
  }
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
  zone                = var.zone
  network             = var.network
  image_name          = var.image_name
  ssh_username        = var.ssh_username
  source_image_family = var.source_image_family
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

    # Migrated the DB to the other instance
    # environment_vars = [
    #   "DB_URL=${var.DB_URL}",
    #   "DB_USERNAME=${var.DB_USERNAME}",
    #   "DB_PASSWORD=${var.DB_PASSWORD}"
    # ]
    script = "packer/scripts/pre-req.sh"
  }


  provisioner "file" {
    source      = "target/healthCheckAPI-0.0.1-SNAPSHOT.jar"
    destination = "/tmp/"
  }

  provisioner "file" {
    source      = "packer/csye6225.service"
    destination = "/tmp/"
  }

  provisioner "file" {
    source      = "packer/csye6225-path.path"
    destination = "/tmp/"
  }

  provisioner "shell" {
    environment_vars = [
      "DB_URL=${var.DB_URL}",
      "DB_USERNAME=${var.DB_USERNAME}",
      "DB_PASSWORD=${var.DB_PASSWORD}"
    ]
    script = "packer/scripts/env-setup.sh"
  }
}
