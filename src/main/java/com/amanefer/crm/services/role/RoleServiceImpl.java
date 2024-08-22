package com.amanefer.crm.services.role;

import com.amanefer.crm.entities.Role;
import com.amanefer.crm.repositories.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    @Value("${app.role.default}")
    private String defaultRole;

    private final RoleRepository roleRepository;

    @Override
    public Role getDefaultRole() {

        return roleRepository.findByName(defaultRole)
                .orElseThrow(() -> new IllegalArgumentException("Such role doesn't exist"));
    }
}
