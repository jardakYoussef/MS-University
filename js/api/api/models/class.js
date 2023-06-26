const mongoose = require('mongoose');
const classSchema = new mongoose.Schema({
  name: {
    type: String,
    required: true,
  },
  students: [{
    studentId: {
      type: mongoose.Schema.Types.ObjectId,
      ref: 'Student',
    },
    name: String,
  }],
});

const Class = mongoose.model('Class', classSchema);

module.exports = Class;
