
// DECLARATIVE //
pipeline{
  agent any
  options{
    timeout(time : 1, unit :'HOURS')
  }
  stages{
    stages("Build"){
      echo "Build no ${BUILD_ID}, made at ${WORKSPACE}"
      bat 'javac Main.java'
    }
    stages("Test"){
      echo "some test mnipulaiton"
      bat 'java Main'
    }
    stages("Deploy"){
      echo "Should be working"
    }
  }
  
}
