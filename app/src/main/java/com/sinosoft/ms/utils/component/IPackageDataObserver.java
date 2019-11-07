/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: frameworks/base/core/java/android/content/pm/IPackageDataObserver.aidl
 */
package com.sinosoft.ms.utils.component;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IPackageDataObserver extends IInterface {
	public static abstract class Stub extends Binder implements
			IPackageDataObserver {
		private static final String DESCRIPTOR = "IPackageDataObserver";
		public Stub() {
			this.attachInterface(this, DESCRIPTOR);
		}

		public static IPackageDataObserver asInterface(
				IBinder obj) {
			if ((obj == null)) {
				return null;
			}
			IInterface iin = (IInterface) obj
					.queryLocalInterface(DESCRIPTOR);
			if (((iin != null) && (iin instanceof IPackageDataObserver))) {
				return ((IPackageDataObserver) iin);
			}
			return new IPackageDataObserver.Stub.Proxy(obj);
		}

		public IBinder asBinder() {
			return this;
		}

		@Override
		public boolean onTransact(int code, Parcel data,
				android.os.Parcel reply, int flags)
				throws RemoteException {
			switch (code) {
			case INTERFACE_TRANSACTION: {
				reply.writeString(DESCRIPTOR);
				return true;
			}
			case TRANSACTION_onRemoveCompleted: {
				data.enforceInterface(DESCRIPTOR);
				String _arg0;
				_arg0 = data.readString();
				boolean _arg1;
				_arg1 = (0 != data.readInt());
				this.onRemoveCompleted(_arg0, _arg1);
				return true;
			}
			}
			return super.onTransact(code, data, reply, flags);
		}

		private static class Proxy implements
				IPackageDataObserver {
			private IBinder mRemote;

			Proxy(IBinder remote) {
				mRemote = remote;
			}

			public IBinder asBinder() {
				return mRemote;
			}

			public String getInterfaceDescriptor() {
				return DESCRIPTOR;
			}

			public void onRemoveCompleted(String packageName,
					boolean succeeded) throws RemoteException {
				Parcel _data = Parcel.obtain();
				try {
					_data.writeInterfaceToken(DESCRIPTOR);
					_data.writeString(packageName);
					_data.writeInt(((succeeded) ? (1) : (0)));
					mRemote.transact(Stub.TRANSACTION_onRemoveCompleted, _data,
							null, IBinder.FLAG_ONEWAY);
				} finally {
					_data.recycle();
				}
			}
		}

		static final int TRANSACTION_onRemoveCompleted = (IBinder.FIRST_CALL_TRANSACTION + 0);
	}

	public void onRemoveCompleted(String packageName,
			boolean succeeded) throws RemoteException;
}
