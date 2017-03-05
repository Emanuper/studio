package es.iessaladillo.pedrojoya.pr114;

import android.Manifest;
import android.app.Activity;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CallLog;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;

import butterknife.BindView;
import butterknife.ButterKnife;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;


@SuppressWarnings("CanBeFinal")
@RuntimePermissions
public class MainActivity extends AppCompatActivity {

    private static final int LIMIT = 20;

    @SuppressWarnings({"WeakerAccess", "unused"})
    @BindView(R.id.lstLlamadas)
    ListView mLstLlamadas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Se carga la lista de últimas llamadas.
        MainActivityPermissionsDispatcher.cargarListaWithCheck(MainActivity.this);
    }

    @NeedsPermission({Manifest.permission.READ_CALL_LOG, Manifest.permission.READ_CONTACTS})
    void cargarLista() {
        int offset = 0;
        String[] campos = {CallLog.Calls._ID, CallLog.Calls.NUMBER, CallLog.Calls.CACHED_NAME,
                           CallLog.Calls.TYPE, CallLog.Calls.CACHED_NUMBER_LABEL, CallLog.Calls
                                   .DATE, CallLog.Calls.DURATION};
        String orden = CallLog.Calls.DATE + " DESC LIMIT " + offset + "," + LIMIT;
        CursorLoader cargador = new CursorLoader(this, CallLog.Calls.CONTENT_URI, campos, null,
                null, orden);
        Cursor cursor = cargador.loadInBackground();
        String[] from = {CallLog.Calls.NUMBER, CallLog.Calls.CACHED_NAME, CallLog.Calls.TYPE,
                         CallLog.Calls.DATE, CallLog.Calls.DURATION};
        int[] to = {R.id.lblNumero, R.id.lblNombre, R.id.imgTipoLlamada, R.id.lblFecha, R.id
                .lblDuracion};
        Adaptador mAdaptador = new Adaptador(this, R.layout.activity_main_item, cursor, from, to,
                0);
        mLstLlamadas.setAdapter(mAdaptador);
    }

    @OnShowRationale({Manifest.permission.READ_CALL_LOG, Manifest.permission.READ_CONTACTS})
    void showRationale(final PermissionRequest request) {
        new AlertDialog.Builder(this).setMessage(
                R.string.permission_rationale).setPositiveButton(
                R.string.solicitar, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        request.proceed();
                    }
                }).setNegativeButton(R.string.rechazar, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                request.cancel();
            }
        }).show();
    }

    @OnPermissionDenied({Manifest.permission.READ_CALL_LOG, Manifest.permission.READ_CONTACTS})
    void showDenied() {
        new AlertDialog.Builder(this).setMessage(R.string.lo_sentimos).setPositiveButton(
                R.string.solicitar, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        MainActivityPermissionsDispatcher.cargarListaWithCheck(MainActivity.this);
                    }
                }).setNegativeButton(R.string.cerrar_app, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        }).show();
    }

    @OnNeverAskAgain({Manifest.permission.READ_CALL_LOG, Manifest.permission.READ_CONTACTS})
    void showNeverAsk() {
        new AlertDialog.Builder(this).setMessage(R.string.lo_sentimos).setPositiveButton(
                R.string.solicitar, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startInstalledAppDetailsActivity(MainActivity.this);
                        finish();
                    }
                }).setNegativeButton(R.string.cerrar_app, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        }).show();


        Snackbar.make(mLstLlamadas, R.string.permission_neverask,
                Snackbar.LENGTH_LONG).setAction(R.string.configurar, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startInstalledAppDetailsActivity(MainActivity.this);
            }
        }).show();
    }

    private static void startInstalledAppDetailsActivity(@NonNull final Activity context) {
        final Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.setData(Uri.parse("package:" + context.getPackageName()));
        // Para que deje rastro en la pila de actividades se añaden flags.
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        context.startActivity(intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
            @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        MainActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode,
                grantResults);
    }


}
