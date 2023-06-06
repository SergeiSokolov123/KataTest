const showUsersRow = document.getElementById('showUsers');

// функция для получения данных с сервера и создания строк таблицы
function showUsers() {
  // отправляем запрос на сервер для получения данных
  fetch('/api/admin')
    .then(response => response.json())
    .then(data => {
      // очищаем таблицу перед обновлением данных
      showUsersRow.innerHTML = '';

      // создаем строку таблицы для каждого элемента в полученных данных
      data.forEach(user => {
        // создаем новую строку таблицы
        const row = document.createElement('tr');
        row.id = `user-${user.id}`;

        // создаем ячейки таблицы для каждого поля пользователя
        const idCell = document.createElement('td');
        idCell.textContent = user.id;
        row.appendChild(idCell);

        const firstNameCell = document.createElement('td');
        firstNameCell.textContent = user.firstName;
        row.appendChild(firstNameCell);

        const lastNameCell = document.createElement('td');
        lastNameCell.textContent = user.lastName;
        row.appendChild(lastNameCell);

        const emailCell = document.createElement('td');
        emailCell.textContent = user.email;
        row.appendChild(emailCell);

        const ageCell = document.createElement('td');
        ageCell.textContent = user.age;
        row.appendChild(ageCell);

        const roleCell = document.createElement('td');
        user.roles.forEach(role => {
          const roleSpan = document.createElement('span');
          roleSpan.textContent = role.name;
          roleCell.appendChild(roleSpan);
        });
        row.appendChild(roleCell);

        // добавляем кнопку "Edit"
        const editCell = document.createElement('td');
        const editButton = document.createElement('button');
        editButton.textContent = 'Edit';
        editButton.classList.add('btn', 'btn-info');
        editButton.setAttribute('data-bs-toggle', 'modal');
        editButton.setAttribute('data-bs-target', '#modalEdit');
        editCell.appendChild(editButton);
        row.appendChild(editCell);

        // добавляем кнопку "Delete"
        const deleteCell = document.createElement('td');
        const deleteButton = document.createElement('button');
        deleteButton.textContent = 'Delete';
        deleteButton.classList.add('btn', 'btn-danger');
        deleteButton.setAttribute('data-bs-toggle', 'modal');
        deleteButton.setAttribute('data-bs-target', '#modalDelete');
        deleteCell.appendChild(deleteButton);
        row.appendChild(deleteCell);

        // обработка нажатия на кнопку "Delete"
        deleteButton.addEventListener('click', () => {
          // заполняем модальное окно данными пользователя
          document.getElementById('deleteId').value = user.id;
          document.getElementById('deleteFirstName').value = user.firstName;
          document.getElementById('deleteLastName').value = user.lastName;
          document.getElementById('deleteEmail').value = user.email;
          document.getElementById('deleteAge').value = user.age;
          document.getElementById('deleteRole').value = user.roles.map(role => role.name).join(', ');
        });

        // обработка нажатия на кнопку "Delete" в модальном окне
        const modalDeleteButton = document.getElementById('modalDeleteButton');
        modalDeleteButton.addEventListener('click', () => {
          const userId = document.getElementById('deleteId').value;
          deleteUser(userId);
          $('#modalDelete').modal('hide');
        });

       // обработка нажатия на кнопку "Edit"
             editButton.addEventListener('click', () => {
                 // заполняем модальное окно данными пользователя
                 document.getElementById('editId').value = user.id;
                 document.getElementById('editFirstName').value = user.firstName;
                 document.getElementById('editLastName').value = user.lastName;
                 document.getElementById('editEmail').value = user.email;
                 document.getElementById('editAge').value = user.age;
                 document.getElementById('editRole').value = user.roles.map(role => role.name).join(', ');
                 document.getElementById('editPassword').value = user.password;
               });
             // обработка нажатия на кнопку "Edit" в модальном окне
                const modalEditButton = document.getElementById('modalEditButton');
                modalEditButton.addEventListener('click', () => {
                const userId = document.getElementById('editId').value;
                updateUser(userId);
                $('#modalEdit').modal('hide');
               });


        // добавляем строку таблицы в таблицу
        showUsersRow.appendChild(row);
      });

      // после создания строк таблицы вызываем функцию для обновления информации о пользователе
      updateUserInfo();
    })
    .catch(error => {
      console.error('Error fetching data:', error);
    });
}

// вызываем функцию для заполнения таблицы при загрузке страницы
showUsers();

function updateUserInfo() {
  fetch('/api/users')
    .then(response => response.json())
    .then(data => {
      const userEmailElement = document.getElementById('userEmail');
      const userRoleElement = document.getElementById('userRole');

      if (data.email) {
        userEmailElement.textContent = data.email;
      } else {
        userEmailElement.textContent = 'Not logged in';
      }

      if (data.roles.length > 0) {
        userRoleElement.textContent = data.roles.map(role => role.name).join(', ');
      } else {
        userRoleElement.textContent = 'No roles';
      }
    })
    .catch(error => {
      console.error('Error fetching user info:', error);
    });
}

function deleteUser(userId) {
  fetch(`/api/admin/${userId}`, {
    method: 'DELETE'
  })
    .then(response => response.json())
    .then(data => {
      // удаляем строку таблицы с удаленным пользователем
      const row = document.getElementById(`user-${userId}`);
      row.remove();
    })
    .catch(error => {
      console.error('Error deleting user:', error);
    });
}
// Функция для обновления пользователя
function updateUser(userId) {
    const userRow = document.getElementById(`user-${userId}`);
   let id = document.getElementById('editId').value;
      let firstName = document.getElementById('editFirstName').value;
      let lastName = document.getElementById('editLastName').value;
      let age = document.getElementById('editAge').value;
      let email = document.getElementById('editEmail').value;
      let password = document.getElementById('editPassword').value;
      let roles = $("#editRole").val()

      let rolesArray = [];
      for (let i = 0; i < roles.length; i++) {
        if (roles[i] === 'ROLE_ADMIN') {
          rolesArray.push({
            'id': 2,
            'role': 'ROLE_ADMIN',
            "authority": "ROLE_ADMIN"
          });
        }
        if (roles[i] === 'ROLE_USER') {
          rolesArray.push({
            'id': 1,
            'role': 'ROLE_USER',
            "authority": "ROLE_USER"
          });
        }
      }
  // Отправляем запрос на сервер для обновления пользователя с указанным ID
  fetch(`/api/admin/${userId}`, {
    method: 'PUT',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify({
      id: id,
      firstName: firstName,
      lastName: lastName,
      email: email,
      age: age,
    password: password,
    roles: rolesArray
    })
  })
    .then(response => {
          console.log(response); // Добавленная строка
          return response.json();
        })
    .then(updatedUser => {
      // Обновляем информацию о пользователе на странице
      userRow.querySelector('td:nth-child(2)').textContent = updatedUser.firstName;
      userRow.querySelector('td:nth-child(3)').textContent = updatedUser.lastName;
      userRow.querySelector('td:nth-child(4)').textContent = updatedUser.email;
      userRow.querySelector('td:nth-child(5)').textContent = updatedUser.age;

      // Закрываем модальное окно редактирования
      $('#modalEdit').modal('hide');
    })
    .catch(error => {
      console.error('Error updating user:', error);
    });
}
// Вызываем функцию для обновления информации о пользователе
updateUserInfo();