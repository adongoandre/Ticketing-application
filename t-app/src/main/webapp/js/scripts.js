document.addEventListener('DOMContentLoaded', function () {
    // Generalized checkbox logic
    function handleCheckboxes(selector) {
        const checkboxes = document.querySelectorAll(selector);
        checkboxes.forEach(checkbox => {
            checkbox.addEventListener('change', function () {
                if (this.checked) {
                    checkboxes.forEach(box => {
                        if (box !== this) {
                            box.checked = false;
                        }
                    });
                }
            });
        });
    }

    // Priority checkboxes logic
    handleCheckboxes('.priority-checkbox');
    
    // Status checkboxes logic
    handleCheckboxes('.status-checkbox');
	
    // Form submission logic
    document.getElementById('myForm').addEventListener('submit', function() {
        const currentDateTime = new Date().toISOString();
        document.getElementById('created_at').value = currentDateTime;
        document.getElementById('updated_at').value = currentDateTime;
    });

    // Image modal logic
    document.querySelectorAll('.thumb').forEach(image => {
        image.addEventListener('click', function () {
            document.getElementById('imageModalContent').src = this.src;
        });
    });
});

// Separate function to handle the modal image display if needed
function showImageInModal(imageSrc) {
    var modalImage = document.getElementById('imageModalContent');
    modalImage.src = imageSrc; // Set the image source in the modal
}

// Event listener to handle image modal functionality
document.addEventListener('DOMContentLoaded', function() {
    var thumbnailImages = document.querySelectorAll('.thumb');

    // Attach click event to all thumbnail images
    thumbnailImages.forEach(function(image) {
        image.addEventListener('click', function() {
            var imgSrc = this.getAttribute('data-image-src');
            showImageInModal(imgSrc);
        });
    });
});
document.addEventListener('DOMContentLoaded', function () {
    var editModal = document.getElementById('edit-ticket');

    editModal.addEventListener('show.bs.modal', function (event) {
        // Button that triggered the modal
        var button = event.relatedTarget;

        // Extract info from data-* attributes
        var num = button.getAttribute('data-num');
        var description = button.getAttribute('data-description');
        var priority = button.getAttribute('data-priority');

        // Update the form inputs
        var modal = this;
        modal.querySelector('#ticketId').value = num;
        modal.querySelector('#ticket').value = description;

        // Uncheck all checkboxes first
        var checkboxes = modal.querySelectorAll('.priority-checkbox');
        checkboxes.forEach(function (checkbox) {
            checkbox.checked = false;
        });

        // Check the correct priority checkbox
        var priorityCheckbox = modal.querySelector('.priority-checkbox[value="' + priority + '"]');
        if (priorityCheckbox) {
            priorityCheckbox.checked = true;
        }

        
    });
});
document.addEventListener('DOMContentLoaded', function () {
    var adminModal = document.getElementById('admin-edit');

    adminModal.addEventListener('show.bs.modal', function (event) {
        // Button that triggered the modal
        var button = event.relatedTarget;

        // Extract info from data-* attributes
        var num = button.getAttribute('data-num');
        var priority = button.getAttribute('data-priority');
        var status = button.getAttribute('data-status');

        // Update the form inputs
        var modal = this;
        modal.querySelector('#ticketId').value = num;

        // Uncheck all checkboxes for priority
        var priorityCheckboxes = modal.querySelectorAll('.priority-checkbox');
        priorityCheckboxes.forEach(function (checkbox) {
            checkbox.checked = false;
        });

        // Uncheck all radio buttons for status
        var statusRadioButtons = modal.querySelectorAll('.status-checkbox');
        statusRadioButtons.forEach(function (radioButton) {
            radioButton.checked = false;
        });

        // Check the correct priority checkbox
        var priorityCheckbox = modal.querySelector('.priority-checkbox[value="' + priority + '"]');
        if (priorityCheckbox) {
            priorityCheckbox.checked = true;
        }

        // Check the correct status radio button
        var statusRadioButton = modal.querySelector('.status-checkbox[value="' + status + '"]');
        if (statusRadioButton) {
            statusRadioButton.checked = true;
        }
    });
});


function setImageInModal(imageUrl) {
    document.getElementById('editTicketImage').src = imageUrl;
}
document.addEventListener('DOMContentLoaded', function() {
    var editModal = document.getElementById('edit-ticket');
    editModal.addEventListener('show.bs.modal', function(event) {
        var button = event.relatedTarget;
        var imageUrl = button.getAttribute('data-image-url');
        setImageInModal(imageUrl);
    });
});

	
    /* Function to add action buttons to a tbody
    function addActionButtons(tbodyId) {
        const tbody = document.getElementById(tbodyId);
        if (tbody) {
            const rows = tbody.querySelectorAll('tr');
            rows.forEach(row => {
                // Count the number of columns in the header to ensure the row has the same number of cells
                const headerCells = row.closest('table').querySelector('thead tr').children;
                const numberOfColumns = headerCells.length;
                
                // Ensure each row has the correct number of cells
                while (row.children.length < numberOfColumns) {
                    const emptyCell = document.createElement('td');
                    row.appendChild(emptyCell);
                }
                
                // Find the actions cell (it should be the last cell)
                let actionsCell = row.children[numberOfColumns - 1];
                
                // Clear existing actionsCell content
                actionsCell.innerHTML = '';

                // Create a container div for the buttons
                const buttonContainer = document.createElement('div');
                buttonContainer.className = 'd-flex flex-row';

                // Create edit button
                const editButton = document.createElement('button');
                editButton.textContent = 'Edit';
                editButton.className = 'btn btn-sm btn-primary me-2';
                editButton.addEventListener('click', () => {
                    // Add your edit functionality here
                    console.log('Edit button clicked for row:', row);
                });

                // Create remove button
                const removeButton = document.createElement('button');
                removeButton.textContent = 'Remove';
                removeButton.className = 'btn btn-sm btn-danger';
                removeButton.addEventListener('click', () => {
                    // Add your remove functionality here
                    console.log('Remove button clicked for row:', row);
                });

                // Append buttons to the container
                buttonContainer.appendChild(editButton);
                buttonContainer.appendChild(removeButton);

                // Append the container to the actions cell
                actionsCell.appendChild(buttonContainer);
            });
        }
    }

    // Add action buttons to each tbody by ID
    addActionButtons('userTableBody');
    addActionButtons('ticketTableBody');*/
