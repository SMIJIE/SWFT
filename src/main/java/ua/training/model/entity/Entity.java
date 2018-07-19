package ua.training.model.entity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.Serializable;

/**
 * Description: This is the general interface for entity
 *
 * @author Zakusylo Pavlo
 */
public interface Entity<ID> extends Serializable {
    long SERIAL_VERSION_UID = 1L;
    /**
     * Logger for Entity classes
     *
     * @see LogManager
     */
    Logger LOGGER = LogManager.getLogger(Entity.class);

    ID getId();
}
