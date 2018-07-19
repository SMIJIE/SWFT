package ua.training.model.entity;

import java.io.Serializable;

/**
 * Description: This is the general interface for entity builder
 *
 * @author Zakusylo Pavlo
 */
public interface EntityBuilder<E> extends Serializable {
    long SERIAL_VERSION_UID = 1L;
    E build();
}
