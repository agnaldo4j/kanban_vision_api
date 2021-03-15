package com.kanban.vision.usecase.exceptions

class OrganizationAlreadyExists(name: String) extends Exception(s"Organization already exists with ${name}")