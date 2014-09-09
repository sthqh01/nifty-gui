package de.lessvoid.nifty.api;

import de.lessvoid.nifty.internal.accessor.NiftyAccessor;
import de.lessvoid.nifty.spi.NiftyRenderDevice;

final class InternalNiftyAccessorImpl extends NiftyAccessor {
    @Override
    public NiftyRenderDevice getRenderDevice(final Nifty nifty) {
      return nifty.getRenderDevice();
    }
}