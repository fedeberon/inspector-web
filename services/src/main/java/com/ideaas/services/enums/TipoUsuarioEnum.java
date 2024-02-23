package com.ideaas.services.enums;

import java.util.stream.Stream;

/**
 *  <p>The function of this enum is to be able to check the type of user
 *  that has logged in, with the necessary role to be able to enter different
 *  templates or parts of the application</p>
 *  <p> The user type name is prefixed with "ROLE_" to make the user type name compatible with
 *  the spring security api, which requires all roles to be prefixed with "ROLE_"
 */
public enum TipoUsuarioEnum {
    ROLE_ADMINISTRADOR(1L),
    ROLE_USUARIO(2L),
    ROLE_CONSULTA(3L),
    ROLE_USUARIO_VIP(4L),
    ROLE_INSPECTOR(5L),
    ROLE_ADMINISTRADOR_OOH(6L),
    ROLE_DESCONOCIDO(0L);

    private Long id;

    private TipoUsuarioEnum(Long idTipoUsuario) {
        this.id = idTipoUsuario;
    }

    public Long getId() {
        return id;
    }

    public static TipoUsuarioEnum tipoUsuarioOf(Long idTipoUsuario) {
        return Stream.of(TipoUsuarioEnum.values())
                .filter(p -> p.getId() == idTipoUsuario)
                .findFirst()
                .orElse(TipoUsuarioEnum.ROLE_DESCONOCIDO);
    }

    public static TipoUsuarioEnum tipoUsuarioOfString(String stringTipoUsuario) {
        return Stream.of(TipoUsuarioEnum.values())
                .filter(p -> p.toString() == stringTipoUsuario)
                .findFirst()
                .orElse(TipoUsuarioEnum.ROLE_DESCONOCIDO);
    }
}
