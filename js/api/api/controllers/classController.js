const Class = require('../models/class');

class ClassController {
  async add(req, res) {
    try {
      const { name } = req.body;

      const newClass = new Class({ name });
      const savedClass = await newClass.save();

      res.status(201).json(savedClass);
    } catch (error) {
      res.status(500).json({ message: 'Error creating class' });
    }
  }

  async fetchAll(req, res) {
    try {
      const classes = await Class.find();
      res.status(200).json(classes);
    } catch (error) {
      res.status(500).json({ message: 'Error retrieving classes' });
    }
  }
  async assignClass(req, res) {
    try {
      const { classId, studentId, studentName } = req.body;

      // Find the class by its ID
      const foundClass = await Class.findById(classId);

      if (!foundClass) {
        return res.status(404).json({ message: 'Class not found' });
      }

      // Create a student object
      const student = {
        studentId: studentId,
        name: studentName,
      };

      // Add the student to the class
      foundClass.students.push(student);

      // Save the updated class
      const updatedClass = await foundClass.save();

      res.status(200).json(updatedClass);
    } catch (error) {
      console.error('Failed to assign student to class:', error);
      res.status(500).json({ message: 'Error assigning student to class' });
    }
  }

}

module.exports = ClassController;
