package pl.masi.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * Dto do nadawania/odbierania uprawnień do testu
 */
@Data
public class ChangePermsRequestDto {

    @NotNull
    private Long userId;

    @NotNull
    private Long testId;

    @NotNull
    private Operation operation;

    @NotNull
    private Permission permission;

    public enum Operation {
        GRANT,
        REVOKE
    }

    public enum Permission {
        READ,
        ALL,
    }
}
