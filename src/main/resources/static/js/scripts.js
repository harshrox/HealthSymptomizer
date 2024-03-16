var optionsArray = ["enlarged_thyroid", "malaise", "slurred_speech", "muscle_pain", "continuous_sneezing", "fatigue", "dizziness", "rusty_sputum", "irritation_in_anus", "dehydration", "inflammatory_nails", "lack_of_concentration", "toxic_look_(typhos)", "increased_appetite", "phlegm", "spotting_ urination", "belly_pain", "movement_stiffness", "chest_pain", "polyuria", "coma", "continuous_feel_of_urine", "distention_of_abdomen", "weakness_of_one_body_side", "small_dents_in_nails", "depression", "vomiting", "itching", "obesity", "drying_and_tingling_lips", "constipation", "swelling_joints", "abnormal_menstruation", "muscle_wasting", "burning_micturition", "sunken_eyes", "mild_fever", "sweating", "receiving_unsterile_injections", "hip_joint_pain", "swollen_extremeties", "bladder_discomfort", "bruising", "watering_from_eyes", "acute_liver_failure", "fluid_overload", "lethargy", "sinus_pressure", "silver_like_dusting", "anxiety", "skin_peeling", "extra_marital_contacts", "spinning_movements", "dark_urine", "high_fever", "prominent_veins_on_calf", "loss_of_appetite", "passage_of_gases", "history_of_alcohol_consumption", "yellowing_of_eyes", "excessive_hunger", "yellow_urine", "patches_in_throat", "irritability", "abdominal_pain", "breathlessness", "blurred_and_distorted_vision", "blister", "swollen_legs", "scurring", "acidity", "weakness_in_limbs", "stiff_neck", "receiving_blood_transfusion", "indigestion", "chills", "joint_pain", "palpitations", "internal_itching", "diarrhoea", "cramps", "red_spots_over_body", "stomach_bleeding", "neck_pain", "skin_rash", "brittle_nails", "painful_walking", "nausea", "red_sore_around_nose", "pain_in_anal_region", "blackheads", "puffy_face_and_eyes", "altered_sensorium", "restlessness", "throat_irritation", "pain_behind_the_eyes", "back_pain", "unsteadiness", "irregular_sugar_level", "ulcers_on_tongue", "shivering", "mucoid_sputum", "swelled_lymph_nodes", "nodal_skin_eruptions", "bloody_stool", "loss_of_balance", "pus_filled_pimples", "runny_nose", "yellowish_skin", "visual_disturbances", "yellow_crust_ooze", "swollen_blood_vessels", "dischromic _patches", "headache", "cough", "fast_heart_rate", "foul_smell_of urine", "redness_of_eyes", "stomach_pain", "cold_hands_and_feets", "muscle_weakness", "mood_swings", "weight_gain", "pain_during_bowel_movements", "swelling_of_stomach", "knee_pain", "family_history", "weight_loss"];



var currentTab = 0; // Current tab is set to be the first tab (0)
showTab(currentTab); // Display the current tab
var count=0

function showTab(n) {
  // This function will display the specified tab of the form ...

  var x = document.getElementsByClassName("tab");
  x[n].style.display = "block";
  // ... and fix the Previous/Next buttons:
  if (n == 0) {
    document.getElementById("prevBtn").style.display = "none";
  } else {
    document.getElementById("prevBtn").style.display = "inline";
  }
  if (n == (x.length - 1)) {
    document.getElementById("nextBtn").innerHTML = "Submit";
  } else {
    document.getElementById("nextBtn").innerHTML = "Next";
  }

}

function nextPrev(n) {

  // This function will figure out which tab to display
  var x = document.getElementsByClassName("tab");
  // Exit the function if any field in the current tab is invalid:
   if (n == 1 && !validateForm()) return false;

  // Hide the current tab:
  x[currentTab].style.display = "none";
  // Increase or decrease the current tab by 1:

  currentTab = currentTab + n;
  // if you have reached the end of the form... :
  if (currentTab >= x.length) {
   currentTab=0

    //...the form gets submitted:
    document.getElementById("regForm").dispatchEvent(new Event('submit'));



  }
  // Otherwise, display the correct tab:
  showTab(currentTab);
}




function validateForm() {
  // This function deals with validation of the form fields
  var x, y, i, valid = true;
  x = document.getElementsByClassName("tab");

  y = x[currentTab].getElementsByTagName("input");

  // A loop that checks every input field in the current tab:
  for (i = 0; i < y.length; i++) {
//  console.log(y[i].value)
    // If a field is empty...
        if ((y[i].type === "text" || y[i].type === "radio" || y[i].tagName === "SELECT") && y[i].value == "") {
          valid = false;
          break; // Exit the loop if any field is found empty
        }
        // Check if any radio button is required and not selected
        if (y[i].type === "radio" && !isRadioGroupSelected(y[i].name)) {
          valid = false;
          break; // Exit the loop if any radio group is not selected
        }
  }

  return valid; // return the valid status
}

function isRadioGroupSelected(name) {
  var radios = document.querySelectorAll('input[type="radio"][name="' + name + '"]');
  for (var i = 0; i < radios.length; i++) {
    if (radios[i].checked) {
      return true; // At least one radio button is selected
    }
  }
  return false; // No radio button is selected
}
// Function to generate a new input element with a corresponding datalist
function createInputWithDatalist() {
    // Create a new div element
    var divElement = document.createElement('div');
    divElement.classList.add('input__box');

    // Create a new input element
    var newInputElement = document.createElement('input');

    // Set attributes for the new input element
    newInputElement.setAttribute('type', 'text');
    newInputElement.setAttribute('placeholder', 'Symptoms');
    newInputElement.setAttribute('list', 'symptoms_' + Date.now()); // Ensure unique ID for each datalist
    newInputElement.classList.add('myInput');


    // Create a new datalist element
    var datalistElement = document.createElement('datalist');
    datalistElement.id = 'symptoms_' + Date.now(); // Unique ID for datalist

    // Populate options for the datalist
    optionsArray.forEach(function(option) {
        var optionElement = document.createElement('option');
        optionElement.value = option;
        datalistElement.appendChild(optionElement);
    });

    // Append the input and datalist elements to the div element
    divElement.appendChild(newInputElement);
    divElement.appendChild(datalistElement);

    // Append the new div element to the container
    var parentElement = document.getElementById('add_input');
    parentElement.appendChild(divElement);
}

// Function to handle the button click event and add a new input element
flag=0
function addButton() {

   if(count<5){
    createInputWithDatalist();
    }
    else{

    if(flag==0){
    var divElement = document.createElement('h1');
        divElement.textContent = "Too many symptoms you gonna die ! pray to god or we will meet in hell";
        var parentElement = document.getElementById('add_input');
        parentElement.appendChild(divElement)
        flag=1

    }
   }
    count=count+1
}

async function submitData(){
console.log("Submit data daba diya user ne.")
var symptoms = Array.from(document.getElementsByClassName("myInput")).map(function(element) {
    return element.value.trim();
  });

      // Gather username, age, and gender
      var username = document.querySelector('input[name="username"]').value.trim();
      var ageinstring = document.querySelector('input[name="age"]:checked').id;
      var gender = document.querySelector('input[name="gender"]:checked').id;
      var age=0
      if(ageinstring==="dot-3"){
        age=0
      }
      if(ageinstring==="dot-4"){
      age=1
      }
      if(ageinstring==="dot-5"){
      age=10
      }

      if(gender==="dot-1") gender="Male"
      if(gender==="dot-2") gender="Female"






 var formattedSymptoms = '"Symptoms": [\n' + symptoms.join(', \n') + '\n]';

   // Construct data object
       var data = {
           username: username,
           age: age,
           gender: gender,
           symptoms: symptoms
       };
 try {
        // Send data to server
        const response = await fetch('/submit', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(data)
        });

        // Parse response as JSON
        const responseData = await response.json();
        console.log('Success:', responseData);
        // Get the container element where the table will be inserted
        const container = document.getElementById('resultContainer');

        // Create a table element
        const table = document.createElement('table');

        // Create table header
        const headerRow = table.insertRow();
        const headerCell1 = headerRow.insertCell(0);
        const headerCell2 = headerRow.insertCell(1);
        const headerCell3 = headerRow.insertCell(2);
        headerCell1.innerHTML = '<b>Disease Name</b>';
        headerCell2.innerHTML = '<b>Percentage</b>';
        headerCell3.innerHTML = '<b>Precautions</b>';

        // Loop through the response data and populate the table rows
        for (const disease in responseData) {
            const row = table.insertRow();
            const cell1 = row.insertCell(0);
            const cell2 = row.insertCell(1);
            const cell3 = row.insertCell(2);
            cell1.textContent = disease;
            cell2.textContent = responseData[disease][0] + '%';
            cell3.textContent = responseData[disease][1];
        }

        // Append the table to the container element
        container.appendChild(table);

        // Handle success response here

    } catch (error) {
        console.error('Error:', error);
        // Handle error here
    }


return true;
}

