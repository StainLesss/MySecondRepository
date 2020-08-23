
// DECLARATIVE //
pipeline{
  agent any
  options{
    timeout(time : 1, unit :'HOURS')
  }
  stages{
    stage("Build"){
      steps{
        echo "Build no ${BUILD_ID}, made at ${WORKSPACE}"
        bat 'javac Main.java'
      }
    }
    stage("Test"){
      steps{
        echo "some test mnipulaiton"
        bat 'java Main'
      }
    }
    stage("Deploy"){
      steps{
        echo "Should be working"
      }
    }
  }
  
}
