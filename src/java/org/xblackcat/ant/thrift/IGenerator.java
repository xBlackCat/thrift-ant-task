package org.xblackcat.ant.thrift;

/**
 * 24.06.2014 15:53
 *
 * @author xBlackCat
 */
public interface IGenerator {
    String getOutputDir();

    String getOptionsString();

    boolean generateSeparated();
}
