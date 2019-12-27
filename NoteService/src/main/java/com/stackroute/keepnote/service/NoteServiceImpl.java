package com.stackroute.keepnote.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stackroute.keepnote.exception.NoteNotFoundExeption;
import com.stackroute.keepnote.model.Note;
import com.stackroute.keepnote.model.NoteUser;
import com.stackroute.keepnote.repository.NoteRepository;

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
public class NoteServiceImpl implements NoteService{

	/*
	 * Autowiring should be implemented for the NoteRepository and MongoOperation.
	 * (Use Constructor-based autowiring) Please note that we should not create any
	 * object using the new keyword.
	 */
	@Autowired
	NoteRepository noteRepositoryImpl;
	/*
	 * This method should be used to save a new note.
	 */
	public boolean createNote(Note note) {

		/*NoteUser noteUser = null;
		try {
		//	getNoteByNoteId(note.getNoteCreatedBy(), note.getNoteId());
		//	getAllNoteByUserId(note.getNoteCreatedBy());
			noteUser = new NoteUser();
			noteUser.setUserId(note.getNoteCreatedBy());
			List<Note> noteList = new ArrayList<>();
			noteList.add(note);
			noteUser.setNotes(noteList);

			noteRepositoryImpl.insert(noteUser);
			return true;
		} catch (Exception e) {
			return false;
		}*/
		/*int counter = 1;
		boolean status = false;
		NoteUser noteUser = new NoteUser();
		List<Note> notes = new ArrayList<>();
		note.setNoteCreationDate(new Date());
		if (noteRepositoryImpl.existsById(note.getNoteCreatedBy())) {
			notes = noteRepositoryImpl.findById(note.getNoteCreatedBy()).get().getNotes();


			Iterator iterator = notes.iterator();
			Note note1 = new Note();
			while (iterator.hasNext()) {

				note1 = (Note) iterator.next();
			}
			note.setNoteId(note1.getNoteId() + 1);
			notes.add(note);
			noteUser.setUserId(note.getNoteCreatedBy());
			noteUser.setNotes(notes);
			if (null != noteRepositoryImpl.save(noteUser)) {

				status = true;
			}
		} else {

			note.setNoteId(1);
			notes.add(note);
			noteUser.setUserId(note.getNoteCreatedBy());
			noteUser.setNotes(notes);

			if (noteRepositoryImpl.insert(noteUser) != null) {
				status = true;
			}
		}
		return status;*/
		Random random = new Random();
		int noteId = random.nextInt(99999);
		note.setNoteId(noteId);
		boolean created = false;
		Optional<NoteUser> noteUser = noteRepositoryImpl.findById(note.getNoteCreatedBy());
		NoteUser userNotes = null;
		if (noteUser.isPresent()) {
			userNotes = noteUser.get();
			userNotes.getNotes().add(note);
			created = noteRepositoryImpl.save(userNotes) != null ? true : false;
		} else {
			userNotes = new NoteUser();
			userNotes.setUserId(note.getNoteCreatedBy());
			userNotes.setNotes(Arrays.asList(note));
			created = noteRepositoryImpl.insert(userNotes) != null ? true : false;
		}

		return created;
	}

	/* This method should be used to delete an existing note. */


	public boolean deleteNote(String userId, int noteId) {

		if(null == userId || userId.isEmpty()) {
			return false;
		}
		try {
			List<Note> noteList = getAllNoteByUserId(userId);

			noteList.removeIf(note -> note.getNoteId() == noteId);

			NoteUser noteUser = new NoteUser();
			noteUser.setUserId(userId);
			noteUser.setNotes(noteList);

			noteRepositoryImpl.save(noteUser);

		}catch (NullPointerException e) {
			throw new NullPointerException();
		}catch (Exception e) {
			return false;
		}

		return true;
	}

	/* This method should be used to delete all notes with specific userId. */


	public boolean deleteAllNotes(String userId) {

		try {
			noteRepositoryImpl.deleteById(userId);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/*
	 * This method should be used to update a existing note.
	 */
	public Note updateNote(Note note, int id, String userId) throws NoteNotFoundExeption {

		if(null == userId || userId.isEmpty() && note.getNoteId() != id) {
			return null;
		}
		try {
			List<Note> noteList = getAllNoteByUserId(userId);

			noteList.removeIf(p -> p.getNoteId() == id);
			noteList.add(note);

			NoteUser noteUser = new NoteUser();
			noteUser.setUserId(userId);
			noteUser.setNotes(noteList);

			noteRepositoryImpl.save(noteUser);
		} catch (Exception e) {
			throw new NoteNotFoundExeption("NoteNotFoundExeption");
		}
		return note;
	}

	/*
	 * This method should be used to get a note by noteId created by specific user
	 */
	public Note getNoteByNoteId(String userId, int noteId) throws NoteNotFoundExeption {

		if(null == userId || userId.isEmpty()) {
			return null;
		}
		try {
			List<Note> noteList = getAllNoteByUserId(userId);
			noteList.stream().filter(p -> p.getNoteId() == noteId).collect(Collectors.toList());
			return noteList.get(0);
		} catch (Exception e) {
			throw new NoteNotFoundExeption("NoteNotFoundExeption");
		}
	}

	/*
	 * This method should be used to get all notes with specific userId.
	 */
	public List<Note> getAllNoteByUserId(String userId) {

		if(null == userId || userId.isEmpty()) {
			return null;
		}
		try {
			return noteRepositoryImpl.findById(userId).get().getNotes();
		} catch (NoSuchElementException e) {
			
		return new ArrayList<Note>();
		}
	}

}
