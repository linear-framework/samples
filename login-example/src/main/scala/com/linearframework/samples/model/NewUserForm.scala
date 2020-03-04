package com.linearframework.samples.model

import com.linearframework.validation._
import org.apache.commons.lang3.StringUtils

case class NewUserForm(
  username: String,
  password: String,
  passwordConfirmation: String
) extends Validation {
  override protected def constraints(): Unit = {

    "newuser.username.required" validates that(username) prohibits { form =>
      StringUtils.isBlank(form.username)
    }

    "newuser.password.required" validates that(password) prohibits { form =>
      StringUtils.isEmpty(form.password)
    }

    "newuser.password.requirements" validates that(password) requires { form =>
      form.password.length >= 8
    }

    "newuser.passwordConfirmation.mismatch" validates that(passwordConfirmation) requires { form =>
      form.password == form.passwordConfirmation
    }

  }
}

