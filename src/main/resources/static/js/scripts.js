//var optionsArray = new Set(["Deep or extensive mouth ulcers","weight gain","painful walking","fluid overload","redness of eyes","stomach bleeding","phlegm","increased appetite","No attachment at all","muscle wasting","Ear pain","lack of concentration","Not well attached to breast","yellowing of eyes","extra marital contacts","swelling of stomach","altered sensorium","Severe chest indrawing","Not able to drink","drinking poorly","distention of abdomen","blurred and distorted vision","enlarged thyroid","knee pain","Pus is seen draining from the ear and discharge is reported for less than 14 days","joint pain","slurred speech","diarrhoea","abnormal menstruation","Lethargic or unconscious","continuous sneezing","Temperature between 35.5 - 36.40C","Restless, irritable","Stiff neck","cramps","coma","indigestion","Sunken eyes","prominent veins on calf","Visible severe wasting","constipation","unsteadiness","runny nose","irritability","rusty sputum","Mouth ulcers","swollen extremeties","chest pain","Not able to feed","bruising","Stridor in calm child","bladder discomfort","puffy face and eyes","irregular sugar level","yellow crust ooze","Oedema of both feet","skin rash","acute liver failure","drying and tingling lips","Not suckling effectively","pain behind the eyes","skin peeling","swelling joints","spotting  urination","high fever","throat irritation","polyuria","receiving unsterile injections","small dents in nails","movement stiffness","Skin pinch goes back slowly","Fever (by history or feels hot or temperature 37.5°C or above)","red spots over body","irritation in anus","Palms and soles yellow","excessive hunger","Axillary temperature 37.5oC or above (or feels hot to touch)","Less than 10 skin pustules","dischromic  patches","continuous feel of urine","patches in throat","scurring","family history","Bulging fontanelle","neck pain","inflammatory nails","lethargy","restlessness","malaise","Pus discharge from ear","mild fever","Fast breathing (60 breaths per minute or more)","fast heart rate","Nasal flaring","receiving blood transfusion","mucoid sputum","pus filled pimples","watering from eyes","Skin pinch goes back very slowly","Less than normal movements","back pain","shivering","passage of gases","Convulsions","anxiety","bloody stool","loss of appetite","Tender swelling behind the ear","pain in anal region","vomiting","internal itching","Blood in the stool","congestion","mood swings","stomach pain","No dehydration","Breast or nipple problems","yellowish skin","Some palmar pallor","weight loss","Receives other foods or drinks","Pus draining from the eye","depression","sunken eyes","swollen blood vessels","headache","muscle weakness","burning micturition","breathlessness","fatigue","history of alcohol consumption","Temperature less than 35.5oC (or feels cold to touch)","Umbilicus red or draining pus","ulcers on tongue","Severe palmar pallor","chills","muscle pain","palpitations","dizziness","10 or more skin pustules or a big boil","nausea","weakness in limbs","Grunting","spinning movements","yellow urine","weakness of one body side","itching","sinus pressure","visual disturbances","Diarrhoea lasting 14 days or more","cough","loss of smell","foul smell of urine","nodal skin eruptions","hip joint pain","abdominal pain","Fast breathing","cold hands and feets","Pus is seen draining from the ear and discharge is reported for 14 days or more","Less than 8 breastfeeds in 24 hours","obesity","blister","belly pain","pain during bowel movements","blood in sputum","blackheads","dark urine","swelled lymph nodes","swollen legs","Drinks eagerly, thirsty","Dehydration present","silver like dusting","Thrush (ulcers or white patches in mouth)","stiff neck","Chest indrawing","Any general danger sign","Clouding of cornea","Age less than 24 hours","Severely Underweight ( < -3 SD)","sweating","acidity","red sore around nose","toxic look (typhos)","loss of balance","dehydration","Age 14 days or more","brittle nails"]);
const optionsArray = []
async function fetchData() {
    try {
        console.log("Inside fetchData")
        const response = await fetch("dataOptions.json");
        const data = await response.json();
        data["options"].forEach(function(option) {
            optionsArray.push(option)
        });
        console.log("Final size of optionsArray: ", optionsArray.length)

    } catch (error) {
        console.error('Error fetching or parsing JSON:', error);
    }
}
var dataOptionsArray = new Set();
fetchData().then(() => {

    optionsArray.forEach(function(option) {
        dataOptionsArray.add(option);
    });
    optionsArray.splice(0, optionsArray.length);
});

var upto2OptionsArray = new Set(["Nasal flaring","Age 14 days or more","Pus discharge from ear","No attachment at all","Receives other foods or drinks","Not able to feed","Not well attached to breast","Age less than 24 hours","Grunting","Axillary temperature 37.5oC or above (or feels hot to touch)","Thrush (ulcers or white patches in mouth)","Palms and soles yellow","Temperature between 35.5 - 36.40C","Not suckling effectively","Breast or nipple problems","Temperature less than 35.5oC (or feels cold to touch)","10 or more skin pustules or a big boil","Less than 8 breastfeeds in 24 hours","Umbilicus red or draining pus","Lethargic or unconscious","Fast breathing (60 breaths per minute or more)","Severe chest indrawing","Convulsions","Bulging fontanelle","Sunken eyes","Less than normal movements","Diarrhoea lasting 14 days or more","Blood in the stool","Less than 10 skin pustules"]);
var twoto5OptionsArray = new Set(["No dehydration","Bulging fontanelle","Lethargic or unconscious","Fever (by history or feels hot or temperature 37.5°C or above)","Drinks eagerly, thirsty","Pus is seen draining from the ear and discharge is reported for 14 days or more","Oedema of both feet","drinking poorly","Restless, irritable","Not able to drink","Some palmar pallor","Stiff neck","Skin pinch goes back slowly","Ear pain","Skin pinch goes back very slowly","Severe palmar pallor","Pus draining from the eye","Clouding of cornea","Visible severe wasting","Chest indrawing","Any general danger sign","Deep or extensive mouth ulcers","Dehydration present","Stridor in calm child","Blood in the stool","Tender swelling behind the ear","Mouth ulcers","Pus is seen draining from the ear and discharge is reported for less than 14 days","Severely Underweight ( < -3 SD)","Sunken eyes","Fast breathing"]);


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
    document.getElementById("contain").style.display = "none";


    console.log("kyun nhi ho rha hide");



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

    var ageinstring = document.querySelector('input[name="age"]:checked').id;
        var optionsArray;

      var age=0
          if(ageinstring==="dot-3"){
            age=0;
            optionsArray=upto2OptionsArray;

          }
          if(ageinstring==="dot-4"){
          age=1;
          optionsArray=twoto5OptionsArray;
          }
          if(ageinstring==="dot-5"){
          age=10;
          optionsArray=dataOptionsArray;
          }

          console.log(optionsArray);


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
    Array.from(optionsArray).forEach(function(option) {
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
        divElement.textContent = "Too many symptoms";
        var parentElement = document.getElementById('add_input');
        parentElement.appendChild(divElement)
        flag=1

    }
   }
    count=count+1
}

async function submitData(){
console.log("Submitted.")
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
        console.log('Success:', responseData)
        fetchData().then(() => {
         optionsArray.forEach(function(option) {
             dataOptionsArray.add(option);
         });
     });

        // Get the container element where the table will be inserted
        const container = document.getElementById('resultContainer');

        // Create a table element
        const table = document.createElement('table');

        // Create table header
        const headerRow = table.insertRow();
        const headerCell1 = headerRow.insertCell(0);
        const headerCell2 = headerRow.insertCell(1);
        const headerCell3 = headerRow.insertCell(2);
        const headerCell4 = headerRow.insertCell(3);
        headerCell1.innerHTML = '<p align="center"><b>Disease</b></p>';
        headerCell2.innerHTML = '<p align="center"><b>Percentage</b></p>';
        headerCell3.innerHTML = '<p align="center"><b>Symptoms</b></p>';
        headerCell4.innerHTML = '<p align="center"><b>Precautions</b></p>';

        // Loop through the response data and populate the table rows
        for (const disease in responseData) {
            const row = table.insertRow();
            const cell1 = row.insertCell(0);
            const cell2 = row.insertCell(1);
            const cell3 = row.insertCell(2);
            const cell4 = row.insertCell(3);
            cell1.textContent = disease;
            cell2.textContent = responseData[disease][0] + '%';
            cell3.textContent = JSON.parse(responseData[disease][2]).join(", ");
            cell4.textContent = responseData[disease][1];
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

