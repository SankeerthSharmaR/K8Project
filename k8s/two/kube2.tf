provider "google" {
  project = "kuber-5902"
  region  = "us-west2"
}

resource "google_container_cluster" "cluster-2" {
  name     = "cluster-2"
  location = "us-west2-a"

  # Optional: Check if the default node pool exists before removing it
  count = var.remove_default_node_pool ? 1 : 0

  remove_default_node_pool = var.remove_default_node_pool

  # Specify the default node pool only if remove_default_node_pool is false
  # This ensures it's not deleted when it doesn't exist
  node_pool {
    name               = "default-pool"
    initial_node_count = 1

    node_config {
      machine_type = "e2-standard-2"
      disk_size_gb = 20
      image_type   = "COS_CONTAINERD"
    }
  }

  node_pool {
    name               = "my-pool"
    initial_node_count = 1

    node_config {
      machine_type = "e2-standard-2"
      disk_size_gb = 20
      image_type   = "COS_CONTAINERD"
    }
  }
}

# Optional: Define a variable to control whether to remove the default node pool
variable "remove_default_node_pool" {
  type    = bool
  default = true
}
