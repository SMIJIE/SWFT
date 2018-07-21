package ua.training.model.dao.service;

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
}
