package com.linearframework.samples.model

import com.linearframework.validation._
import org.apache.commons.lang3.StringUtils

case class LoginForm(
  username: String,
  password: String
) extends Validation {
  override protected def constraints(): Unit = {

    "login.username.required" validates that(username) prohibits { form =>
      StringUtils.isBlank(form.username)
    }

    "login.password.required" validates that(password) prohibits { form =>
      StringUtils.isEmpty(form.password)
    }

  }
}

