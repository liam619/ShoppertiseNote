package shoppertise.notes.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import shoppertise.notes.exception.EmptyInputException;
import shoppertise.notes.exception.NoElementFoundException;
import shoppertise.notes.model.GroupByTags;
import shoppertise.notes.model.Note;
import shoppertise.notes.service.NoteService;

@RestController
@CrossOrigin(origins = "http://localhost:5000")
@RequestMapping("/shoppertise")
public class NoteController {

    private static final int SIZE = 5;
    
    @Autowired
    private NoteService noteService;
    
    /** Public endpoint for system status check. **/
    @GetMapping("/status")
    public ResponseEntity<String> getAppStatus() {
        return ResponseEntity.status(HttpStatus.OK).body("Spring boot is up and running.");
    }
    
    /** Add new note **/
    @PostMapping("/addNote")
    public ResponseEntity<String> createNote(@RequestBody Note note) {
        boolean isNull = note.getTitle() == null || note.getTags() == null;
        if (isNull) {
            throw new NullPointerException("Title or Tags is missing!");
        }
        
        boolean isEmpty = note.getTitle().isBlank() || note.getTags().isEmpty();
        if (isEmpty) {
            throw new EmptyInputException("Title or Tags cannot be empty!");
        }
        
        return new ResponseEntity<String>(noteService.createNote(note), HttpStatus.OK);
    }
    
    /** Fetch all notes by page **/
    @GetMapping("/getAllNotesByPage")
    public ResponseEntity<Page<Note>> getNotesByPage(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "title") String sortBy) {
                
        Page<Note> notesByPage = noteService.getNotesByPage(page, SIZE, sortBy);

        return new ResponseEntity<Page<Note>>(notesByPage, HttpStatus.OK);
    }
    
    /** Fetch all notes by group **/
    @GetMapping("/getAllNotesByGroup")
    public ResponseEntity<List<GroupByTags>> getNotesByGroup(@RequestParam(defaultValue = "asc") String sortBy) {
        List<GroupByTags> notesByGroup = noteService.getNotesByGroup(sortBy);
        
        if (notesByGroup.isEmpty()) {
            throw new NoElementFoundException("No notes found.");
        }

        return new ResponseEntity<List<GroupByTags>>(notesByGroup, HttpStatus.OK);
    }
    
    /** Fetch specific note **/
    @GetMapping("/getNote/{title}")
    public ResponseEntity<Note> getNote(@PathVariable String title) {
        Note note = noteService.getNote(title);
        
        if (note != null) {
            return new ResponseEntity<Note>(note, HttpStatus.OK);
        }
        
        throw new NoElementFoundException(title + " not found.");
    }
    
    /** Update specific note **/
    @PutMapping("/note/{title}")
    public ResponseEntity<Note> updateNote(@PathVariable String title, @RequestBody Note note) {
        Note updateNote = noteService.updateNote(title, note);

        if (updateNote != null) {
            return new ResponseEntity<Note>(updateNote, HttpStatus.OK);
        }
        
        throw new NoElementFoundException(title + " not found.");
    }
    
    /** Delete single note **/
    @DeleteMapping("/remove/{title}")
    public ResponseEntity<String> deleteNote(@PathVariable String title) {
        boolean deleteNote = noteService.deleteNote(title);
        
        if (deleteNote) {
            return new ResponseEntity<String>(title + " delete from database.", HttpStatus.OK);
        }
        
        throw new NoElementFoundException(title + " not found.");
    }
}
