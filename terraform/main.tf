provider "google" {
  project = "your-gcp-project-id"
  region  = "us-central1"
}

resource "google_compute_instance" "default" {
  name         = "instance-name"
  machine_type = "e2-medium"
  zone         = "us-central1-a"

  boot_disk {
    initialize_params {
      image = "debian-cloud/debian-9"
    }
  }

  network_interface {
    network = "default"

    access_config {
    }
  }
}

resource "google_sql_database_instance" "default" {
  name             = "instance-name"
  database_version = "POSTGRES_12"
  region           = "us-central1"

  settings {
    tier = "db-f1-micro"
  }
}

resource "google_sql_database" "default" {
  name     = "database-name"
  instance = google_sql_database_instance.default.name
}

resource "google_sql_user" "default" {
  name     = "user-name"
  instance = google_sql_database_instance.default.name
  password = "your-password"
}

resource "google_compute_backend_service" "default" {
  name                  = "backend-service"
  health_checks         = [google_compute_health_check.default.self_link]
  load_balancing_scheme = "EXTERNAL"
}

resource "google_compute_health_check" "default" {
  name = "health-check"

  http_health_check {
    request_path = "/"
    port         = "80"
  }
}

resource "google_compute_url_map" "default" {
  name            = "url-map"
  default_service = google_compute_backend_service.default.self_link
}

resource "google_compute_target_http_proxy" "default" {
  name   = "http-proxy"
  url_map = google_compute_url_map.default.self_link
}

resource "google_compute_global_forwarding_rule" "default" {
  name       = "forwarding-rule"
  target     = google_compute_target_http_proxy.default.self_link
  port_range = "80"
}