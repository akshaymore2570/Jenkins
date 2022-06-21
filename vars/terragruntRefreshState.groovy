//
//  Author: Hari Sekhon
//  Date: 2022-01-07 18:48:47 +0000 (Fri, 07 Jan 2022)
//
//  vim:ts=2:sts=2:sw=2:et
//
//  https://github.com/HariSekhon/Jenkins
//
//  License: see accompanying Hari Sekhon LICENSE file
//
//  If you're using my code you're welcome to connect with me on LinkedIn and optionally send me feedback to help steer this or other code I publish
//
//  https://www.linkedin.com/in/HariSekhon
//

// For large estates to run a separate refresh-only job periodically to keep the state file up to date
//
// $APP and $ENVIRONMENT must be set in pipeline to ensure separate locking

def call(timeoutMinutes=59){
  String label = "Terragrunt Refresh State - App: $APP, Environment: $ENVIRONMENT"
  // must differentiate lock to share the same lock as Terraform Plan and Terraform Apply
  String lock = "Terraform - App: $APP, Environment: $ENVIRONMENT"
  lock(resource: lock, inversePrecedence: true) {
    // forbids older runs from starting
    milestone(label: "Milestone: $label")

    // terragrunt docker image is pretty useless, doesn't have the tools to authenticate to cloud providers
    //container('terragrunt') {
      timeout(time: timeoutMinutes, unit: 'MINUTES') {
        ansiColor('xterm') {
          dir(System.getenv("TERRAFORM_DIR") ?: ".") {
            // for test environments, add a param to trigger -destroy switch
            echo "$label"
            sh (
              label: "$label",
              script: 'terragrunt apply -refresh-only --terragrunt-non-interactive -input=false'
            )
          }
        }
      }
    //}
  }
}
