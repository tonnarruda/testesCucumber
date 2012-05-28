package com.fortes.model;

import java.io.Serializable;

/**
 * Base class for Model objects.  Child objects should implement toString(),
 * equals() and hashCode();
 *
 * @author Igo Coelho
 */
public abstract class BaseObject implements Serializable
{
    public abstract String toString();
    public abstract boolean equals(Object o);
    public abstract int hashCode();
}