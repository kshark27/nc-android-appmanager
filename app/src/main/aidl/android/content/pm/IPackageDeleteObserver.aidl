/**  * Created by nhancao on 10/21/16.  */

package android.content.pm;

/**
 * API for deletion callbacks from the Package Manager.
 *
 * {@hide}
 */
oneway interface IPackageDeleteObserver {
    void packageDeleted(in String packageName, in int returnCode);
}

