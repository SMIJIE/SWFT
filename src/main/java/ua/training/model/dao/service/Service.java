package ua.training.model.dao.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.training.model.dao.DaoFactory;

/**
 * Description: This is the generic service for dao operations
 *
 * @author Zakusylo Pavlo
 */
public interface Service {
    /**
     * DaoFactory connection for Service classes
     *
     * @see DaoFactory
     */
    DaoFactory DAO_FACTORY = DaoFactory.getInstance();
    /**
     * Logger for Service classes
     *
     * @see LogManager
     */
    Logger LOGGER = LogManager.getLogger(Service.class);
}
