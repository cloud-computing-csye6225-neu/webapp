variable "zone" {
  type = string
}

variable "project_id" {
  type = string
}

variable "ssh_username" {
  type    = string
  default = "packer"
}

variable "network" {
  type = string
}

variable "image_name" {
  type = string
}

variable "source_image_family" {
  type = string
}
