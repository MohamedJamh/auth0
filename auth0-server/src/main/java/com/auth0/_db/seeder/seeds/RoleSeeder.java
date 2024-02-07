package com.auth0._db.seeder.seeds;
import com.auth0.domain.entity.Permission;
import com.auth0.domain.entity.Role;
import com.auth0.repository.PermissionRepository;
import com.auth0.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class RoleSeeder {
    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;

    private final Set<String> roles = Set.of(
            "SUPER_ADMIN",
            "ADMIN",
            "MANAGER",
            "USER"
    ); // add your roles here
    public void seed() {
        if(roleRepository.count() == 0){
            roles.forEach(
                role ->{
                    Role roleToSave = Role.builder()
                            .name(role.toUpperCase())
                            .build();
                    if("SUPER_ADMIN".equals(role)){
                        Optional<Permission> superPermission = permissionRepository
                                .findBySubjectAndAction("all", "manage");
                        superPermission.ifPresent(permission -> roleToSave.setPermissions(Set.of(permission)));
                    }
                    roleRepository.save(
                            roleToSave
                    );
                }
            );
        }
    }
}
