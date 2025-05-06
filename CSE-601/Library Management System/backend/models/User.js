import mongoose from 'mongoose';

const UserSchema = new mongoose.Schema({
  
  // id: {
  //   type: Number,
  //   increment: true,
  //   unique: true,
  //   required: [true, 'Please add a user ID'],
  //   trim: true
  // },
  name: {
    type: String,
    required: [true, 'Please add a name'],
    trim: true
  },
  email: {
    type: String,
    required: [true, 'Please add an email'],
    unique: true,
    match: [
      /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/,
      'Please add a valid email'
    ]
  },
  role: {
    type: String,
    enum: ['student', 'faculty', 'admin'],
    default: 'student'
  }
}, {
  timestamps: true
});

const User = mongoose.model('User', UserSchema);
export default User;