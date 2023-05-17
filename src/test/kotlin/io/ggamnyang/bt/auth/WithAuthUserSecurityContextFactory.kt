package io.ggamnyang.bt.auth

import io.ggamnyang.bt.domain.entity.User
import io.ggamnyang.bt.service.userdetail.UserDetailsAdapter
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContext
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.test.context.support.WithSecurityContextFactory

class WithAuthUserSecurityContextFactory : WithSecurityContextFactory<WithAuthUser> {
    override fun createSecurityContext(annotation: WithAuthUser?): SecurityContext {
        val user = User(annotation!!.username, "password")

        val token = UsernamePasswordAuthenticationToken(
            UserDetailsAdapter(user),
            "password",
            listOf(SimpleGrantedAuthority(annotation.role))
        )

        val context = SecurityContextHolder.getContext()
        context.authentication = token

        return context
    }
}
