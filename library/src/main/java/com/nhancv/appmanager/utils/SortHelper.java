/**
 * Created by nhancao on 10/21/16.
 */
package com.nhancv.appmanager.utils;

import com.nhancv.appmanager.modules.appmanager.AppItem;

import java.io.File;
import java.text.Collator;
import java.util.Comparator;

public class SortHelper {

    public static final Comparator<AppItem> sAppComparator = new Comparator<AppItem>() {
        private final Collator collator = Collator.getInstance();

        public final int compare(final AppItem a, final AppItem b) {
            return collator.compare(a.getLabel(), b.getLabel());
        }
    };

    public static final Comparator<File> sFileComparator = new Comparator<File>() {
        @Override
        public int compare(final File lhs, final File rhs) {
            // if we have a directory and a file, we want the directory to win
            if (lhs.isDirectory() && !rhs.isDirectory()) return -1;
            // same for a file and a directory
            if (!lhs.isDirectory() && rhs.isDirectory()) return 1;
            // if we have two files or two directories, we let the filename decide
            return lhs.getName().toLowerCase().compareTo(rhs.getName().toLowerCase());
        }
    };

}
