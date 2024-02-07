package com.auth0._db.seeder.seeds;


import com.auth0.domain.entity.Permission;
import com.auth0.repository.PermissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class PermissionSeeder {
    private final PermissionRepository permissionRepository;

    private final Set<String> subjects = Set.of(
            "all",
            "user",
            "role",
            "task"
    ); // add your subjects here
    private final Set<String> actions = Set.of(
            "manage", // this is a special action that grants all actions
            "create",
            "read",
            "update",
            "delete"
    ); // add your actions here
    private final Set<String> fixedPermissions = Set.of(
            "permission:read"
    ); // add your fixed permissions here

    public void seed() {
        if(permissionRepository.count() == 0){
            subjects.forEach(subject ->
                actions.forEach(action ->
                    permissionRepository.save(
                        Permission.builder()
                                .subject(subject)
                                .action(action)
                                .build()
                    )
                )
            );
            fixedPermissions.forEach(permission ->
                permissionRepository.save(
                    Permission.builder()
                            .subject(permission.split(":")[0])
                            .action(permission.split(":")[1])
                            .build()
                )
            );
        }
    }
}
