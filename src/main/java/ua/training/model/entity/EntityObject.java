package ua.training.model.entity;

import java.io.Serializable;

/**
 * Description: This is the general interface for entity
 *
 * @author Zakusylo Pavlo
 */
public interface EntityObject<ID> extends Serializable {
    long SERIAL_VERSION_UID = 1L;

    ID getId();
}
