package dk.kea.studentdtodat23c.service;

import dk.kea.studentdtodat23c.dto.StudentRequestDTO;
import dk.kea.studentdtodat23c.dto.StudentResponseDTO;
import dk.kea.studentdtodat23c.model.Student;
import dk.kea.studentdtodat23c.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    // Constructor injection
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<StudentResponseDTO> getAllStudents() {
        /*
        List<Student> students = studentRepository.findAll();

        List<StudentResponseDTO> studentResponseDTOs = new ArrayList<>();

        // Using a for-loop to convert each Student to a StudentResponseDTO
        for (Student student : students) {
            StudentResponseDTO studentResponseDTO = new StudentResponseDTO(student.getId(), student.getName(), student.getBornDate(), student.getBornTime());
            studentResponseDTOs.add(studentResponseDTO);
        }

        return studentResponseDTOs;
        */
        //use functional programming style
        return studentRepository.findAll().stream()
                .map(student -> new StudentResponseDTO(
                        student.getId(),
                        student.getName(),
                        student.getBornDate(),
                        student.getBornTime()))
                .toList();
                //.collect(Collectors.toList());
    }

    public StudentResponseDTO getStudentById(Long id) {
        /*
        Optional<Student> optionalStudent = studentRepository.findById(id);

        // Throw RuntimeException if student is not found
        if (optionalStudent.isEmpty()) {
            throw new RuntimeException("Student not found with id " + id);
        }

        Student student = optionalStudent.get();

        return new StudentResponseDTO(student.getId(), student.getName(), student.getBornDate(), student.getBornTime());
        */
        //functional programming style
        return studentRepository.findById(id)
                .map(student -> new StudentResponseDTO(
                        student.getId(),
                        student.getName(),
                        student.getBornDate(),
                        student.getBornTime()))
                .orElseThrow(() -> new RuntimeException("Student with id " + id + " not found"));
    }

    public StudentResponseDTO createStudent(StudentRequestDTO studentRequestDTO) {
        /* Student student = new Student();
        student.setName(studentRequestDTO.name());
        student.setPassword(studentRequestDTO.password());
        student.setBornDate(studentRequestDTO.bornDate());
        student.setBornTime(studentRequestDTO.bornTime());
        */

        /* Student newStudent = new Student(studentRequestDTO.name(),
                studentRequestDTO.password(),
                studentRequestDTO.bornDate(),
                studentRequestDTO.bornTime());

         */
        Student newStudent = Student.builder()
                .name(studentRequestDTO.name())
                .password(studentRequestDTO.password())
                .bornDate(studentRequestDTO.bornDate())
                .bornTime(studentRequestDTO.bornTime())
                .build();

        Student studentResponse = studentRepository.save(newStudent);

        return new StudentResponseDTO(studentResponse.getId(), studentResponse.getName(), studentResponse.getBornDate(), studentResponse.getBornTime());
    }

    public StudentResponseDTO updateStudent(Long id, StudentRequestDTO studentRequestDTO) {
        /*
        Optional<Student> optionalStudent = studentRepository.findById(id);
        // Throw RuntimeException if student is not found
        if (optionalStudent.isEmpty()) {
            throw new RuntimeException("Student not found with id " + id);
        }

        Student student = optionalStudent.get();
        */
        //functional programming style
        Student student = studentRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("Student with id " + id + " not found"));

        student.setName(studentRequestDTO.name());
        student.setPassword(studentRequestDTO.password());
        student.setBornDate(studentRequestDTO.bornDate());
        student.setBornTime(studentRequestDTO.bornTime());

        Student studentResponse = studentRepository.save(student);
        return new StudentResponseDTO(studentResponse.getId(), studentResponse.getName(), studentResponse.getBornDate(), studentResponse.getBornTime());
    }

    public void deleteStudent(Long id) {
        if (!studentRepository.existsById(id)) {
            // Throw RuntimeException if student is not found
            throw new RuntimeException("Student not found with id " + id);
        }
        studentRepository.deleteById(id);
    }
}
