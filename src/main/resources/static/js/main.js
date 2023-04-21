$('#delete-row').click(function () {
	$(".delete-modal").css('display', 'flex');
});

$('.delete-options a').click(function () {
	$(".delete-modal").css('display', 'none');
});
$('.user-profile').click(function () {
	$(".user-logout-link").show();
});
$('.user-logout-link').click(function () {
	$(this).hide();
});

function readURL(input, idName) {
	removeElementsByClass("alert alert-danger imageValidate");
	console.log(input)
	if (input.files && input.files[0]) {
		const fileType = file['type'];
		if (!fileType.includes('image')) {
			var node = document.createElement("P");
			node.className = "alert alert-danger imageValidate";
			var textnode = document.createTextNode("Please select a valid image format.");
			node.appendChild(textnode);
			document.getElementById(idName).append(node);
			$(input).val('');
		} else {
			removeElementsByClass("alert alert-danger imageValidate")
			var reader = new FileReader();

			reader.onload = function (e) {
				console.log("e", e)
				$('#uploadedCardImage').attr('src', e.target.result).width(164).height(164);
			};

			reader.readAsDataURL(input.files[0]);
		}
		$(".img-box").addClass("view-img");
	}
}

function removeElementsByClass(className) {
	const elements = document.getElementsByClassName(className);
	while (elements.length > 0) {
		elements[0].parentNode.removeChild(elements[0]);
	}
}
function isValidDate(dateString, idName) {
	console.log(dateString);
	removeElementsByClass("alert alert-danger dateValidate");
	var regEx = /^([0-2][0-9]|(3)[0-1])(\/)(((0)[0-9])|((1)[0-2]))(\/)\d{4}$/i;
	if (dateString && !dateString.match(regEx)) {
		console.log("in error")
		var node = document.createElement("P");
		node.className = "alert alert-danger dateValidate";
		var textnode = document.createTextNode("Please correct the date format");
		node.appendChild(textnode);
		document.getElementById(idName).append(node);
	} else {
		console.log("in correct format")
		removeElementsByClass("alert alert-danger dateValidate")
	}
}

$(function () {
	$("#delete-row").hide();
	var current = location.pathname;
	$('div.header-right a').each(function () {
		var $this = $(this);
		// if the current path is like this link, make it active
		if ($this.attr('href').indexOf(current) !== -1) {
			$this.addClass('active');
		} else {
			$this.removeClass('active');
		}
	})
})

function valueChanged() {
	if ($('.child-checkbox').is(":checked"))
		$("#delete-row").show();
	else
		$("#delete-row").hide();
}

