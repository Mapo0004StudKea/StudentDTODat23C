package dk.kea.studentdtodat23c.service;

import dk.kea.studentdtodat23c.dto.StudentRequestDTO;
import dk.kea.studentdtodat23c.dto.StudentResponseDTO;
import dk.kea.studentdtodat23c.model.Student;
import dk.kea.studentdtodat23c.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    // Constructor injection
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<StudentResponseDTO> getAllStudents() {
        List<Student> students = studentRepository.findAll();
        List<StudentResponseDTO> studentResponseDTOs = new ArrayList<>();

        // Using a for-loop to convert each Student to a StudentResponseDTO
        for (Student student : students) {
            StudentResponseDTO studentResponseDTO = new StudentResponseDTO(student.getId(), student.getName(), student.getBornDate(), student.getBornTime());
            studentResponseDTOs.add(studentResponseDTO);
        }

        return studentResponseDTOs;
    }

    public StudentResponseDTO getStudentById(Long id) {
        Optional<Student> optionalStudent = studentRepository.findById(id);

        // Throw RuntimeException if student is not found
        if (optionalStudent.isEmpty()) {
            throw new RuntimeException("Student not found with id " + id);
        }

        Student student = optionalStudent.get();

        return new StudentResponseDTO(student.getId(), student.getName(), student.getBornDate(), student.getBornTime());
    }

    public StudentResponseDTO createStudent(StudentRequestDTO studentRequestDTO) {
        Student newStudent = new Student(studentRequestDTO.name(), studentRequestDTO.password(), studentRequestDTO.bornDate(), studentRequestDTO.bornTime());
        Student studentResponse = studentRepository.save(newStudent);

        return new StudentResponseDTO(studentResponse.getId(), studentResponse.getName(), studentResponse.getBornDate(), studentResponse.getBornTime());
    }

    public Student updateStudent(Long id, Student studentRequest) {
        Optional<Student> optionalStudent = studentRepository.findById(id);
        // Throw RuntimeException if student is not found
        if (optionalStudent.isEmpty()) {
            throw new RuntimeException("Student not found with id " + id);
        }

        Student student = optionalStudent.get();

        student.setName(studentRequest.getName());
        student.setPassword(studentRequest.getPassword());
        student.setBornDate(studentRequest.getBornDate());
        student.setBornTime(studentRequest.getBornTime());

        Student studentResponse = studentRepository.save(student);
        return studentResponse;
    }

    public void deleteStudent(Long id) {
        if (!studentRepository.existsById(id)) {
            // Throw RuntimeException if student is not found
            throw new RuntimeException("Student not found with id " + id);
        }
        studentRepository.deleteById(id);
    }
}
