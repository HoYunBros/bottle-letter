package io.ggamnyang.bt.auth

import org.springframework.security.test.context.support.WithSecurityContext

@Retention(AnnotationRetention.RUNTIME)
@WithSecurityContext(factory = WithAuthUserSecurityContextFactory::class)
annotation class WithAuthUser(
    val username: String,
    val role: String
)
