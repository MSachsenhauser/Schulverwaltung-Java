$(document).ready(function()
		{
			$("#form").submit(function()
					{
						parent.closeDialog();
						this.submit(); // use the native submit method of the form element
					});
		}
);