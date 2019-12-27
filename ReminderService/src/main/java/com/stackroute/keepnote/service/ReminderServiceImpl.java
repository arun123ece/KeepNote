package com.stackroute.keepnote.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stackroute.keepnote.exception.ReminderNotCreatedException;
import com.stackroute.keepnote.exception.ReminderNotFoundException;
import com.stackroute.keepnote.model.Reminder;
import com.stackroute.keepnote.repository.ReminderRepository;

/*
 * Service classes are used here to implement additional business logic/validation 
 * This class has to be annotated with @Service annotation.
 * @Service - It is a specialization of the component annotation. It doesn't currently 
 * provide any additional behavior over the @Component annotation, but it's a good idea 
 * to use @Service over @Component in service-layer classes because it specifies intent 
 * better. Additionally, tool support and additional behavior might rely on it in the 
 * future.
 * */
@Service
public class ReminderServiceImpl implements ReminderService {

	/*
	 * Autowiring should be implemented for the ReminderRepository. (Use
	 * Constructor-based autowiring) Please note that we should not create any
	 * object using the new keyword.
	 */

	/*
	 * This method should be used to save a new reminder.Call the corresponding
	 * method of Respository interface.
	 */
	@Autowired
	ReminderRepository reminderRepositoryImpl;

	/*public Reminder createReminder(Reminder reminder) throws ReminderNotCreatedException {

		try {
			// Reminder reminderVo = getReminderById(reminder.getReminderId());
			 reminderRepositoryImpl.insert(reminder);
			 return reminder;
		} catch (Exception e) {
			throw new ReminderNotCreatedException("ReminderNotCreatedException");
		}
	}*/
	public Reminder createReminder(Reminder reminder) throws ReminderNotCreatedException {
		
        reminder.setReminderCreationDate(new Date());
        Reminder reminder1 = reminderRepositoryImpl.insert(reminder);
        
        if (reminder1 == null) {
            throw new ReminderNotCreatedException("Unable to create Reminder!!!! Please try again");
        }
        return reminder1;
    }

	/*
	 * This method should be used to delete an existing reminder.Call the
	 * corresponding method of Respository interface.
	 */
	public boolean deleteReminder(String reminderId) throws ReminderNotFoundException {

		if(null == reminderId || reminderId.isEmpty()) {
			return false;
		}
		try {
			Reminder reminder = getReminderById(reminderId);
			reminderRepositoryImpl.delete(reminder);
			return true;
		} catch (Exception e) {
			throw new ReminderNotFoundException("ReminderNotFoundException");
		}
	}

	/*
	 * This method should be used to update a existing reminder.Call the
	 * corresponding method of Respository interface.
	 */
	public Reminder updateReminder(Reminder reminder, String reminderId) throws ReminderNotFoundException {

		if(null == reminderId || reminderId.isEmpty() || null == reminder) {
			return null;
		}
		try {
			 reminderRepositoryImpl.save(reminder);
			 return reminder;
		} catch (Exception e) {

			throw new ReminderNotFoundException("ReminderNotFoundException");
		}
	}

	/*
	 * This method should be used to get a reminder by reminderId.Call the
	 * corresponding method of Respository interface.
	 */
	public Reminder getReminderById(String reminderId) throws ReminderNotFoundException {

		if(null == reminderId || reminderId.isEmpty()) {
			return null;
		}
		try {
			return reminderRepositoryImpl.findById(reminderId).get();
		} catch (Exception e) {

			throw new ReminderNotFoundException("ReminderNotFoundException");
		}
	}

	/*
	 * This method should be used to get all reminders. Call the corresponding
	 * method of Respository interface.
	 */

	public List<Reminder> getAllReminders() {

		return reminderRepositoryImpl.findAll();
	}

}
