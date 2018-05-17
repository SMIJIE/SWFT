package ua.training.model.entity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Description: This is the general interface for entity
 *
 * @author Zakusylo Pavlo
 */
public interface Entity<ID> {
    /**
     * Logger for Entity classes
     *
     * @see LogManager
     */
    Logger LOGGER = LogManager.getLogger(Entity.class);

    ID getId();
}
