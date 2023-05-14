#!/usr/bin/env groovy
//
//  Author: Hari Sekhon
//  Date: 2023-05-14 04:54:05 +0100 (Sun, 14 May 2023)
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

// ========================================================================== //
//                    Jenkins Groovy Shared Library Function
// ========================================================================== //

def call(image, tag) {
  if (!image) {
    error "no docker image passed as first arg to dockerAddTagIfNotExists() function"
  }
  if (!tag) {
    error "no docker tag passed as second arg to dockerAddTagIfNotExists() function"
  }
  if (!image.contains(':')) {
    image += ":$tag"
  }
  return image
}