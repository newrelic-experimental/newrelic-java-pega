package com.nr.instrumentation.pega.pegarules;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import com.newrelic.api.agent.NewRelic;

public class PegaConfigService  {
	
	private ScheduledFuture<?> future = null;
	public static boolean initialized = false;
	
	public static void init() {
		try {
			PegaConfigService service = new PegaConfigService();
			service.doStart();
			Runtime.getRuntime().addShutdownHook(new StopActivity(service));
			initialized = true;
		} catch (Exception e) {
			NewRelic.getAgent().getLogger().log(Level.FINE, e, "Problem initializing Pega Configuration");
		}
	}
	
	public PegaConfigService() {
		
	}

	protected void doStart() throws Exception {
		NewRelic.getAgent().getLogger().log(Level.FINE, "Starting Pega Configuration Service");
		PegaConfiguration config = new PegaConfiguration();
		future = Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(config, 5L, 1L, TimeUnit.MINUTES);
	}

	protected void doStop() throws Exception {
		if(future != null) {
			boolean stopped = future.cancel(true);
			if(stopped) {
				NewRelic.getAgent().getLogger().log(Level.FINE, "Stopped periodic checks of Pega Configuration");
			} else {
				NewRelic.getAgent().getLogger().log(Level.FINE, "Failed to stop periodic checks of Pega Configuration");
			}
		} else {
			NewRelic.getAgent().getLogger().log(Level.FINE, "Stopping PegaConfigService but no future object available to stop");
		}
	}

	private static class StopActivity extends Thread {
		
		private PegaConfigService service = null;
		
		private StopActivity(PegaConfigService s) {
			service = s;
		}

		@Override
		public void run() {
			if(service != null) {
				try {
					service.doStop();
				} catch (Exception e) {
					NewRelic.getAgent().getLogger().log(Level.FINE, e, "Failed on call to stop configuration service");
				}
			}
		}
		
	}
}
