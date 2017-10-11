package br.com.getset.calendarchurch.timer;

import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import br.com.getset.calendarchurch.dao.EventDao;
import br.com.getset.calendarchurch.service.PushNotificationService;

public class EventTimerTaskSendEvents implements ServletContextListener {

	private EventDao eDao;
	private PushNotificationService pnService;

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		System.out.println("TimeTaskSendEvents destroyed");
	}

	@Override
	public void contextInitialized(ServletContextEvent servletContextEvent) {

		System.out.println("TimeTaskSendEvents initialized.");
		
		TimerTask evtTimer = new EventTimerTaskInternal();

		Timer timer = new Timer();

		Calendar dataIni = new GregorianCalendar();
		// dataIni.add(Calendar.DAY_OF_MONTH,1);
		dataIni.set(Calendar.HOUR_OF_DAY, 20);
		dataIni.set(Calendar.MINUTE, 0);
		dataIni.set(Calendar.SECOND, 0);

		// 86400 = qtd segundos em 24hs
		long periodo = 86400 * 1000;

		timer.schedule(evtTimer, dataIni.getTime(), periodo);

	}

	class EventTimerTaskInternal extends TimerTask {
		@Override
		public void run() {
			try{
				if(eDao == null){
					eDao = new EventDao(true);
				}
				if(pnService == null)
					pnService = new PushNotificationService();
				
				Calendar tomorrow = Calendar.getInstance();
				tomorrow.add(Calendar.DAY_OF_MONTH, 1);
				eDao.findByDay(tomorrow);
				if(tomorrow.get(Calendar.HOUR_OF_DAY) == 20)
					pnService.sendNotificationBasic("Agenda de amanhã - Miash", eDao.findByDay(tomorrow),"Evento");
			}catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
